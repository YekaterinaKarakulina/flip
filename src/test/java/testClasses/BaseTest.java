package testClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    private static WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeSuite
    public WebDriver beforeSuite() {
        System.out.println("Before Suite");
        System.setProperty("webdriver.gecko.driver", "src/test/java/resources/geckodriver");
        driver = new FirefoxDriver();
        //System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver76.exe");
        //WebDriver driver = new ChromeDriver();
        getDriver().manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getToPage(getDriver(), "https://flip.kz");
        return driver;
    }

    @BeforeTest
    public WebDriver beforeTest() {
        findByXpathAndClick(getDriver(), "//a[contains(@href,'1') and contains(text(), 'Книги')]");
        findByXpathAndClick(getDriver(), "//a[@data-filter-field-sections-id='44']");
        return driver;
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("Before class");
    }

    @BeforeGroups(value = {"trigonometric"})
    public void beforeGroupTrigonometric() {
        System.out.println("Before group named 'trigonometric'");
    }


    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before method");
    }


    @AfterMethod
    public void goToBasePage() throws InterruptedException {
        System.out.println("After Test");
        findByXpathAndClick(getDriver(), "//div[contains(@class,'logo cell')]");
        findByXpathAndClick(getDriver(), "//a[contains(@href,'1') and contains(text(), 'Книги')]");
        findByXpathAndClick(getDriver(), "//a[@data-filter-field-sections-id='44']");
        Thread.sleep(5000); //will change to some wait
    }


    @AfterMethod
    public void afterMethod() {
        System.out.println("After method");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("After class");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After suite");
        getDriver().close();
    }


    private static void getToPage(WebDriver driver, String URL) {
        driver.get(URL);
    }

    public void findByXpathAndClick(WebDriver driver, String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    public void findByXpathAndType(WebDriver driver, String xpath, String wordToType) {
        driver.findElement(By.xpath(xpath)).sendKeys(wordToType);
    }


}
