package selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class FirefoxWebDriverLinux implements WebDriverInterface {

    private static WebDriver driver = null;
    private static RemoteWebDriver remoteWebDriver = null;

    private static final String GECKO_WD_LINUX = "webdriver.gecko.driver";
    private static final String GECKO_WD_LINUX_PATH = "src/test/resources/drivers/geckodriver";

    @Override
    public WebDriver createLocalWebDriver() {
        System.setProperty(GECKO_WD_LINUX, GECKO_WD_LINUX_PATH);
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
