package testClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
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
        System.setProperty("webdriver.gecko.driver", "src/test/java/resources/geckodriver"); //this for my home PC with linux
        driver = new FirefoxDriver();
        //System.setProperty("webdriver.chrome.driver", "src/test/java/resources/chromedriver76.exe");
        //driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        getToPage(driver, "https://flip.kz");
        return driver;
    }

    @AfterMethod
    public void goToMainPage() {
        findByXpathAndClick(getDriver(), "//div[contains(@class,'logo cell')]");
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

    protected void waitUntilClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void scrollToElement(By by) {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("arguments[0].scrollIntoView(false)", getDriver().findElement(by));
    }

    protected void scrollToElement(WebElement webElement) {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("arguments[0].scrollIntoView(false)", webElement);
    }

    protected void scrollToTheEndOfPage() {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    protected void waitUntilSearchIsReady() {
        new WebDriverWait(getDriver(), 60)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(ExpectedConditions.attributeToBe(getDriver().findElement(By.xpath(".//div[@id='content']")),
                        "style", "opacity: 1;"));
    }

    protected void waitPageForLoad() {
        new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }
}
