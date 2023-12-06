import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class ImageSearchTest {
    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver","C:\\WebDriver\\bin\\chromedriver.exe");
        driver = new ChromeDriver(chromeOptions);
    }

    @Test
    @Parameters({"searchResultIndex", "uploadImagePath", "searchResultImagePath", "screenShotPath"})
    public void testImageSearch(String searchResultIndex, String uploadImagePath, String searchResultImagePath, String screenShotPath) throws IOException {
        driver.get("https://www.baidu.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        // Upload picture, Set wait duration 3 seconds for page elements loading
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        //WebElement searchByImageButton = new WebDriverWait(driver,Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class=\"soutu-btn\"]")));
        WebElement searchByImageButton = driver.findElement(By.xpath("//span[@class=\"soutu-btn\"]"));
        wait.until(d -> searchByImageButton.isDisplayed());
        searchByImageButton.click();
//        WebElement uploadFile = new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type=\"file\" and @class=\"upload-pic\"]")));
        WebElement uploadFile = driver.findElement(By.xpath("//input[@type=\"file\" and @class=\"upload-pic\"]"));
        Path path = Paths.get(uploadImagePath);
        uploadFile.sendKeys(path.toAbsolutePath().toString());

        // Get search result index from config file parameters - "searchResultIndex"
        // Xpath ends with ***div[3]/a[1]/img, a[1] represents row index is 1 and div[3] represents the index of image in a[1] row, both of row and cell indexes start from 1
        WebElement searchResultImage = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[3]/div/div[2]/div/div['" + searchResultIndex + "']/a[1]/img"));
        //searchResultImage.click();

        // Verify whether search result is a picture file
        Assert.assertEquals(searchResultImage.getTagName(),"img", "Actual search result is expected. Actual search result refers to" +searchResultImage.getAttribute("src") );

        //Download search result picture in index of configured parameter to local disk(path parameter is configured in xml)
        ImageDownloadUtils.downloadPicture(searchResultImage.getAttribute("src"),searchResultImagePath);

        //Incapable of collecting information from baidu picture search result page, and not be able to compare two pictures by simply making assertion
        //Bypass: download search result picture to disk, Compare them locally, and set file compare measurement parameter to 0.1
        double p = ImageCompareUtils.imageCompare(uploadImagePath,searchResultImagePath);
        Assert.assertTrue(p < 0.1,"Two pictures are quite different");

        // Save last page screenshot in local
        ScreenshotUtils.takeScreenshot(driver, screenShotPath);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}