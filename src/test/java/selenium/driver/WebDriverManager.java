package selenium.driver;

import org.openqa.selenium.WebDriver;
import selenium.service.FileReaderJsonAndProperties;

public class WebDriverManager {

    private static WebDriver driver = null;
    private static String browserName = FileReaderJsonAndProperties.readDriver("browserName");
    private static String browserType = FileReaderJsonAndProperties.readDriver("browserType");

    private WebDriverManager() {
    }

    public static WebDriver getWebDriverInstance() {
        if (browserType.equals("local")) {
            if (driver == null) {
                driver = new WebDriverFactory().getLocalDriver(browserName);
            }
        } else if (browserType.equals("remote")) {
            if (driver == null) {
                driver = new WebDriverFactory().getRemoteDriver(browserName);
            }
        }
        driver.manage().window().maximize();
        return driver;
    }

}
