package selenium.driver;

import org.openqa.selenium.WebDriver;

public interface WebDriverInterface {
    WebDriver createLocalWebDriver();
    WebDriver createRemoteWebDriver();
}
