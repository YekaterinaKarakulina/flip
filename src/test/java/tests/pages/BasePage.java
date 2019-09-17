package tests.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    private static final Duration MAX = Duration.ofSeconds(60);
    private static final Duration POLLING = Duration.ofSeconds(1);
    private static final int WAIT_FOR_ELEMENT_TIMEOUT_SECONDS = 60;

    private WebDriver driver;

    @FindBy(xpath = ".//div[@id='content']")
    private WebElement pageContent;

    @FindBy(xpath = "//table[@id='prod']//*[contains(@href,'people')]")
    protected List<WebElement> bookAuthorsList;

    BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebDriver getDriver() {
        return driver;
    }

    protected void dragAndDropWebElementToPosition(WebElement element, int x, int y) {
        waitForElementEnabled(element);
        new Actions(driver).dragAndDropBy(element, x, y).build().perform();
    }

    protected void sendKeysToWebElement(WebElement element, String key) {
        waitForElementEnabled(element);
        new Actions(driver).sendKeys(element, key).build().perform();
    }

    protected void sendKeys(Keys key) {
        new Actions(driver).sendKeys(key).build().perform();
    }

    protected void clickToWebElement(WebElement element) {
        waitForElementEnabled(element);
        highlightElement(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void moveToWebElement(WebElement element) {
        waitForElementEnabled(element);
        new Actions(driver).moveToElement(element).build().perform();
    }

    protected void highlightElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", element);
    }

    protected void scrollToElement(WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", webElement);
    }

    protected void scrollToTheEndOfPage() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }


    protected void waitForElementEnabled(WebElement element) {
        new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT_SECONDS).until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitUntilSearchIsReady() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(MAX)
                .pollingEvery(POLLING);
        wait.ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(ExpectedConditions.attributeToBe(pageContent,
                        "style", "opacity: 1;"));
    }

    protected void waitUntilElementHasText(WebElement element, String text) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(MAX)
                .pollingEvery(POLLING);
        wait.ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    protected void waitUntilElementsAttributeHasText(WebElement element, String attribute, String value) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(MAX)
                .pollingEvery(POLLING);
        wait.ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(ExpectedConditions.attributeToBe(element, attribute, value));
    }

    protected void waitForListWebElementsVisible(List<WebElement> webElements) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(MAX)
                .pollingEvery(POLLING);
        wait.ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(ExpectedConditions.visibilityOfAllElements(webElements));
    }

}
