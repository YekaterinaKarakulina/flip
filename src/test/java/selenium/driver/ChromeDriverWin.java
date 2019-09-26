package selenium.driver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;

public class ChromeDriverWin implements WebDriverLocal {

    private static WebDriver driver = null;

    private static final String CHROME_WD_WIN = "webdriver.chrome.driver";
    private static final String CHROME_WD_WIN_PATH = "src/test/resources/drivers/chromedriver76.exe";

    @Override
    public WebDriver createWebDriver() {
        System.setProperty(CHROME_WD_WIN, CHROME_WD_WIN_PATH);
        driver = new ChromeDriver();
        return driver;
    }

    @Override
    public void quiteWebDriver() {
        driver.close();
    }
}