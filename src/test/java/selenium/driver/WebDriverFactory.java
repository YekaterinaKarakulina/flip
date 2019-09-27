package selenium.driver;

import org.openqa.selenium.WebDriver;

public class WebDriverFactory {

    public WebDriver getLocalDriver(String browser) {
        switch (browser) {
            case "chromeWin":
                return new LocalChromeDriverWin().createLocalWebDriver();
            case "firefoxLinux":
                return new LocalGeckoDriverLinux().createLocalWebDriver();
            case "firefoxWin":
                return new LocalGeckoDriverWin().createLocalWebDriver();
            default:
                throw new RuntimeException("WebDriver type or name incorrect. Go to `driverSelenium.properties` file and change properties");
        }
    }

    public WebDriver getRemoteDriver(String browser) {
        switch (browser) {
            case "chromeWin":
                return new RemoteWebDriverChrome().createRemoteWebDriver();
            case "firefoxWin":
                return new RemoteWebDriverFirefox().createRemoteWebDriver();
            default:
                throw new RuntimeException("WebDriver type or name incorrect. Go to `driverSelenium.properties` file and change properties");
        }
    }

}
