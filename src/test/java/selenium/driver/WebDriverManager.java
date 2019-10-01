package selenium.driver;

import org.openqa.selenium.WebDriver;
import selenium.reporting.MyLogger;
import selenium.service.FileReaderJsonAndProperties;

public class WebDriverManager {

    private static WebDriver driver = null;
    private static String browserName = FileReaderJsonAndProperties.readDriver("browserName");
    private static String browserType = FileReaderJsonAndProperties.readDriver("browserType");

    private WebDriverManager() {
    }

    public static WebDriver getWebDriverInstance() {
        if (driver == null) {
            driver = new WebDriverFactory().getDriver(browserType, browserName);
            MyLogger.info(String.format("WebDriver get the instance of %s webDriver - %s", browserType, browserName));
        }
        driver.manage().window().maximize();
        return driver;
    }

}
