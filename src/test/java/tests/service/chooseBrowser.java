package tests.service;

import org.openqa.selenium.WebDriver;

import tests.service.drivers.chromeDriverWin;
import tests.service.drivers.geckoDriverLinux;

public class chooseBrowser {

    private static final String CHROME_WD_WIN = "webdriver.chrome.driver";
    private static final String CHROME_WD_WIN_PATH = "src/test/resources/drivers/chromedriver76.exe";
    private static final String GECKO_WD_LINUX = "webdriver.gecko.driver";
    private static final String GECKO_WD_LINUX_PATH = "src/test/java/resources/geckodriver";

    public static WebDriver initBrowser() {
        WebDriver driver = null;
        String browser = driverReader.readDriver();
        switch (browser) {
            case "chromeWin":
                System.setProperty(CHROME_WD_WIN, CHROME_WD_WIN_PATH);
                driver = chromeDriverWin.chromeDriverWin();
                break;
            case "firefoxLinux":
                System.setProperty(GECKO_WD_LINUX, GECKO_WD_LINUX_PATH);
                driver = geckoDriverLinux.geckoDriverLinux();
                break;
            default:
                System.out.println("Browser not selected");
        }
        return driver;
    }
}





