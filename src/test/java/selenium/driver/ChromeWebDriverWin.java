package selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import selenium.reporting.MyLogger;

import java.net.MalformedURLException;
import java.net.URL;

public class ChromeWebDriverWin implements WebDriverInterface {

    private static WebDriver driver = null;
    private static RemoteWebDriver remoteWebDriver = null;
    private URL url;

    private static final String CHROME_WD_WIN = "webdriver.chrome.driver";
    private static final String CHROME_WD_WIN_PATH = "src/test/resources/drivers/chromedriver76.exe";

    @Override
    public WebDriver createLocalWebDriver() {
        System.setProperty(CHROME_WD_WIN, CHROME_WD_WIN_PATH);
        driver = new ChromeDriver();
        return driver;
    }

    @Override
    public WebDriver createRemoteWebDriver() {
        remoteWebDriver = new RemoteWebDriver(getURL(), DesiredCapabilities.chrome());
        return remoteWebDriver;
    }

    private URL getURL() {
        try {
            url = new URL("http://127.0.0.1:4444/wd/hub");
        } catch (MalformedURLException e) {
            MyLogger.error(e.getMessage(), e);
        }
        return url;
    }

}
