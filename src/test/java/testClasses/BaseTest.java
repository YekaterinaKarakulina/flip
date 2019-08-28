package testClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;

public class BaseTest {
    private static WebDriver driver;
    private Duration max = Duration.ofSeconds(60);
    private Duration polling = Duration.ofSeconds(1);

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeSuite
    public WebDriver beforeSuite() {
        //System.setProperty("webdriver.gecko.driver", "src/test/java/resources/geckodriver"); //this for my home PC with linux
        //driver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "src/test/java/resources/chromedriver76.exe");
        driver = new ChromeDriver();
 /*       driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);*/
        driver.manage().window().maximize();
        getToPage(driver, "https://flip.kz");
        return driver;
    }

    @AfterMethod
    public void goToMainPage() {
        waitUntilClickable(getDriver().findElement(By.xpath("//div[contains(@class,'logo cell')]")));
        findByXpathAndClick(getDriver(), "//div[contains(@class,'logo cell')]");
    }

    @AfterSuite
    public void afterSuite() {
        //driver.close();
    }

    private static void getToPage(WebDriver driver, String URL) {
        driver.get(URL);
    }

    public void findByXpathAndClick(WebDriver driver, String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    public void findByXpathAndType(WebDriver driver, String xpath, String text) {
        driver.findElement(By.xpath(xpath)).sendKeys(text);
    }


    protected void waitUntilClickable(WebElement element) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(max)
                .pollingEvery(polling);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitUntilSearchIsReady() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(max)
                .pollingEvery(polling);
        wait.ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(ExpectedConditions.attributeToBe(getDriver().findElement(By.xpath(".//div[@id='content']")),
                        "style", "opacity: 1;"));
    }

    protected void waitUntilElementHasText(By by, String text) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(max)
                .pollingEvery(polling);
        wait.ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(ExpectedConditions.textToBePresentInElement(getDriver().findElement(by), text));
    }

    protected void scrollToElement(WebElement webElement) {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("arguments[0].scrollIntoView(false)", webElement);
    }

    protected void scrollToTheEndOfPage() {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    protected void scrollToElement(By by) {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("arguments[0].scrollIntoView(false)", getDriver().findElement(by));
    }

    protected void waitPageForLoad() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(max)
                .pollingEvery(polling);
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }


}

