package selenium.driver;

import org.openqa.selenium.WebDriver;
import selenium.reporting.MyLogger;

public class WebDriverFactory {

    public WebDriver getDriver(String browserType, String browserName) {
        switch (browserType) {
            case "remote":
                switch (browserName) {
                    case "chromeWin":
                        return new ChromeWebDriverWin().createRemoteWebDriver();
                    case "firefoxLinux":
                        return new FirefoxWebDriverLinux().createRemoteWebDriver();
                    case "firefoxWin":
                        return new FirefoxWebDriverWin().createRemoteWebDriver();
                    default:
                        MyLogger.error("WebDriver type or name incorrect. Go to `driverSelenium.properties` file and change properties");
                        throw new RuntimeException("WebDriver type or name incorrect. Go to `driverSelenium.properties` file and change properties");
                }
            case "local":
                switch (browserName) {
                    case "chromeWin":
                        return new ChromeWebDriverWin().createLocalWebDriver();
                    case "firefoxLinux":
                        return new FirefoxWebDriverLinux().createLocalWebDriver();
                    case "firefoxWin":
                        return new FirefoxWebDriverWin().createLocalWebDriver();
                    default:
                        MyLogger.error("WebDriver type or name incorrect. Go to `driverSelenium.properties` file and change properties");
                        throw new RuntimeException("WebDriver type or name incorrect. Go to `driverSelenium.properties` file and change properties");
                }
            default:
                MyLogger.error("WebDriver type or name incorrect. Go to `driverSelenium.properties` file and change properties");
                throw new RuntimeException("WebDriver type or name incorrect. Go to `driverSelenium.properties` file and change properties");
        }
    }

}
