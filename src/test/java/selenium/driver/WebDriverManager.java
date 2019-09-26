package selenium.driver;

import org.openqa.selenium.WebDriver;
import selenium.service.FileReaderJsonAndProperties;

public class WebDriverManager {

    private static WebDriver driver = null;
    private static String browser = FileReaderJsonAndProperties.readDriver();

    private WebDriverManager() {
    }

    public static WebDriver getWebDriverInstance() {
        if (driver == null) {
           driver = new WebDriverFactory().getLocalDriver(browser);
        }
        driver.manage().window().maximize();
        return driver;
    }

}
