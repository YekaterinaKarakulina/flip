package selenium.driver;

import org.openqa.selenium.WebDriver;

public class WebDriverFactory {

    public WebDriver getLocalDriver(String browser) {
        switch (browser) {
            case "chromeWin":
                return new ChromeDriverWin().createWebDriver();
            case "firefoxLinux":
                return new GeckoDriverLinux().createWebDriver();
            case "firefoxWin":
                return new GeckoDriverWin().createWebDriver();
        }
        return null;
    }

}
