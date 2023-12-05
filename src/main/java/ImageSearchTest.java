import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
    @Parameters({"searchResultIndex", "imagePath", "screenShotPath"})
    public void testImageSearch(int searchResultIndex, String imagePath, String screenShotPath) throws IOException {
        driver.get("https://images.google.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        // Upload picture, Set wait duration 3 seconds for page elements loading
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement searchByImageButton = new WebDriverWait(driver,Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@aria-label=\"Search by image\" and @role=\"button\"]")));
        wait.until(d -> searchByImageButton.isDisplayed());
        searchByImageButton.click();
        WebElement uploadFileLink = new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'upload a file') and @role=\"button\"]")));
        uploadFileLink.click();
        //WebElement uploadButton = driver.findElement(By.xpath("//input[@type='file']"));
        WebElement uploadButton = driver.findElement(By.name("encoded_image"));
        Path path = Paths.get(imagePath);
        uploadButton.sendKeys(path.toAbsolutePath().toString());

        // Get search result index from config file parameters - "searchResultIndex"
        // data-row represents the row index and data-ri represents the index of image, and index start from 0
        WebElement searchResultImage = driver.findElement(By.cssSelector("div[data-ri = '" + (searchResultIndex-1) + "'] img"));
        searchResultImage.click();

        // Verify search result, uploaded a local pic filled with a keyword letter "A"
        Assert.assertTrue(driver.getTitle().contains("A"), "Expected and actual results do not match. Expected : " + "A" + " and Actual : " + driver.getTitle() + ". For more information please refer to picture: " + driver.getCurrentUrl());

        // Save last page screenshot in local
        ScreenshotUtils.takeScreenshot(driver, screenShotPath);

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}