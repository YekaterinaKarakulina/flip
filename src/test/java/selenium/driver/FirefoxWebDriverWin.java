package selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class FirefoxWebDriverWin implements WebDriverInterface {

    private static WebDriver driver = null;
    private static RemoteWebDriver remoteWebDriver = null;


    private static final String GECKO_WD_WIN = "webdriver.gecko.driver";
    private static final String GECKO_WD_WIN_PATH = "src/test/resources/drivers/geckodriverWin.exe";

    @Override
    public WebDriver createLocalWebDriver() {
        System.setProperty(GECKO_WD_WIN, GECKO_WD_WIN_PATH);
        driver = new FirefoxDriver();
        return driver;
    }

    @Override
    public WebDriver createRemoteWebDriver() {
        try {
            remoteWebDriver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), DesiredCapabilities.firefox());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return remoteWebDriver;
    }


}
