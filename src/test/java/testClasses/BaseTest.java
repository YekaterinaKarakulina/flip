package testClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

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
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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
      //  driver.close();
    }


    private static void getToPage(WebDriver driver, String URL) {
        driver.get(URL);
    }

    public void findByXpathAndClick(WebDriver driver, String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    protected void waitUntilClickable(WebElement element){
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void scrollToElement(WebElement element){
        JavascriptExecutor jse = (JavascriptExecutor)getDriver();
        jse.executeScript("arguments[0].scrollIntoView(true)", element);
    }
}
