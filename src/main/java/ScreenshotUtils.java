import org.openqa.selenium.WebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtils {

    public static void takeScreenshot(WebDriver driver, String screenShotPath) {
        // WebDriver turn to TakesScreenshot
        TakesScreenshot screenshotDriver = (TakesScreenshot) driver;

        // Get screenShotFile
        File screenShotFile = screenshotDriver.getScreenshotAs(OutputType.FILE);

        try {
            // Copy file to configured path
            FileUtils.copyFile(screenShotFile, new File(screenShotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}