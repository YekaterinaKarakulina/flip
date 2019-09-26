package selenium.driver;

import org.openqa.selenium.WebDriver;
import selenium.service.FileReaderJsonAndProperties;

public class WebDriverFactory {

    private static String browser = FileReaderJsonAndProperties.readDriver();

    public WebDriver getLocalDriver() {
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
