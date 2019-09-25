package selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import selenium.service.FileReaderJsonAndProperties;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class WebDriverSingleton implements WebDriverCreator{

    private static final String CHROME_WD_WIN = "webdriver.chrome.driver";
    private static final String CHROME_WD_WIN_PATH = "src/test/resources/drivers/chromedriver76.exe";
    private static final String GECKO_WD_LINUX = "webdriver.gecko.driver";
    private static final String GECKO_WD_LINUX_PATH = "src/test/resources/drivers/geckodriver";
    private static final String GECKO_WD_WIN = "webdriver.gecko.driver";
    private static final String GECKO_WD_WIN_PATH = "src/test/resources/drivers/geckodriverWin.exe";

    private static WebDriver driver = null;
    private static String browser = FileReaderJsonAndProperties.readDriver();

    private WebDriverSingleton() {
    }

    public static WebDriver getWebDriverInstance() {
        if (driver == null) {
            switch (browser) {
                case "chromeWin":
                    System.setProperty(CHROME_WD_WIN, CHROME_WD_WIN_PATH);
                    driver = new ChromeDriver();
                    break;
                case "firefoxLinux":
                    System.setProperty(GECKO_WD_LINUX, GECKO_WD_LINUX_PATH);
                    driver = new FirefoxDriver();
                    break;
                case "firefoxWin":
                    System.setProperty(GECKO_WD_WIN, GECKO_WD_WIN_PATH);
                    driver = new FirefoxDriver();
                    break;
                default:
                    System.out.println("Browser not selected");
            }
        }
        driver.manage().window().maximize();
        return driver;
    }

}
