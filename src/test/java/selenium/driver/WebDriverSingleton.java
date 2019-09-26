package selenium.driver;

import org.openqa.selenium.WebDriver;
import selenium.service.FileReaderJsonAndProperties;

public class WebDriverSingleton {

    private static WebDriver driver = null;
    private static String browser = FileReaderJsonAndProperties.readDriver();

    private WebDriverSingleton() {
    }

    public static WebDriver getWebDriverInstance() {
        if (driver == null) {
            switch (browser) {
                case "chromeWin":
                    driver = new ChromeDriverWin().createWebDriver();
                    break;
                case "firefoxLinux":
                    driver = new GeckoDriverLinux().createWebDriver();
                    break;
                case "firefoxWin":
                    driver = new GeckoDriverWin().createWebDriver();
                    break;
                default:
                    System.out.println("Browser not selected");
            }
        }
        driver.manage().window().maximize();
        return driver;
    }

    public static void quiteWebDriver(){
        switch (browser) {
            case "chromeWin":
                new ChromeDriverWin().quiteWebDriver();
                break;
            case "firefoxLinux":
                new GeckoDriverLinux().quiteWebDriver();
                break;
            case "firefoxWin":
                new GeckoDriverWin().quiteWebDriver();
                break;
        }
    }

}
