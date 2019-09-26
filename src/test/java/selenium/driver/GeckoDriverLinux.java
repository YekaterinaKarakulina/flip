package selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GeckoDriverLinux implements WebDriverLocal {

    private static WebDriver driver = null;

    private static final String GECKO_WD_LINUX = "webdriver.gecko.driver";
    private static final String GECKO_WD_LINUX_PATH = "src/test/resources/drivers/geckodriver";

    @Override
    public WebDriver createWebDriver() {
        System.setProperty(GECKO_WD_LINUX, GECKO_WD_LINUX_PATH);
        driver = new FirefoxDriver();
        return driver;
    }

    @Override
    public void quiteWebDriver() {
        driver.close();
    }

}
