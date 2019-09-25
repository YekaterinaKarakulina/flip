package selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public interface WebDriverCreator {
    WebDriver driver = null;
    RemoteWebDriver remoteDriver = null;

    static WebDriver getWebDriverInstance(){
        return driver;
    }

    static RemoteWebDriver getRemoteWebDriverInstance(){
        return remoteDriver;
    }

}
