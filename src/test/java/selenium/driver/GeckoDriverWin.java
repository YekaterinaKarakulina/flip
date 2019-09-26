package selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GeckoDriverWin implements WebDriverLocal{

    private static WebDriver driver = null;

    private static final String GECKO_WD_WIN = "webdriver.gecko.driver";
    private static final String GECKO_WD_WIN_PATH = "src/test/resources/drivers/geckodriverWin.exe";

    @Override
    public WebDriver createWebDriver() {
        System.setProperty(GECKO_WD_WIN, GECKO_WD_WIN_PATH);
        driver = new FirefoxDriver();
        return driver;
    }

}
