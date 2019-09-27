package selenium.driver;

import org.openqa.selenium.WebDriver;

public class WebDriverFactory {

    public WebDriver getDriver(String browserType, String browserName) {
        switch (browserType) {
            case "remote":
                switch (browserName) {
                    case "chromeWin":
                        return new RemoteWebDriverChrome().createWebDriver();
                    case "firefoxWin":
                        return new RemoteWebDriverFirefox().createWebDriver();
                    default:
                        throw new RuntimeException("WebDriver type or name incorrect. Go to `driverSelenium.properties` file and change properties");
                }
            case "local":
                switch (browserName) {
                    case "chromeWin":
                        return new LocalChromeDriverWin().createWebDriver();
                    case "firefoxLinux":
                        return new LocalGeckoDriverLinux().createWebDriver();
                    case "firefoxWin":
                        return new LocalGeckoDriverWin().createWebDriver();
                    default:
                        throw new RuntimeException("WebDriver type or name incorrect. Go to `driverSelenium.properties` file and change properties");
                }
            default:
                throw new RuntimeException("WebDriver type or name incorrect. Go to `driverSelenium.properties` file and change properties");
        }
    }

}
