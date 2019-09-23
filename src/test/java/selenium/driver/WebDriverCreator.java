package selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public interface WebDriverCreator {

    public static WebDriver initWebDriver(){
        return initWebDriver();
    }

    public static RemoteWebDriver initRemoteWebDriver(){
        return initRemoteWebDriver();
    }

}
