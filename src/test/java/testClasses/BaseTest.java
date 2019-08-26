package testClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
        //System.setProperty("webdriver.gecko.driver", "src/test/java/resources/geckodriver"); //this for my home PC with linux
        //driver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "src/test/java/resources/chromedriver76.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        getToPage(driver, "https://flip.kz");
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
    public void afterMethod() {
        System.out.println("After method");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("After class");
    }

    @AfterSuite
    public void afterSuite() {
        driver.close();
    }


    private static void getToPage(WebDriver driver, String URL) {
        driver.get(URL);
    }

    public void findByXpathAndClick(WebDriver driver, String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

}
