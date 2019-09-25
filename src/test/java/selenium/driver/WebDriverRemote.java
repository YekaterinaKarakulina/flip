package selenium.driver;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import selenium.service.FileReaderJsonAndProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class WebDriverRemote implements WebDriverCreator{

    private static RemoteWebDriver remoteWebDriver;
    private static String browser = FileReaderJsonAndProperties.readDriver();

    public static RemoteWebDriver getRemoteWebDriverInstance() {
        if (remoteWebDriver == null) {
            if (browser.equals("remote")) {
                try {
                    remoteWebDriver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), DesiredCapabilities.chrome());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
        return remoteWebDriver;
    }

}
