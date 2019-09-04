package testClasses.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    private static final Duration MAX = Duration.ofSeconds(30);
    private static final Duration POLLING = Duration.ofSeconds(1);
    private static final int WAIT_FOR_ELEMENT_TIMEOUT_SECONDS = 30;
    private WebDriver driver;

    @FindBy(xpath = ".//div[@id='content']")
    private WebElement pageContent;

    BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebDriver getDriver() {
        return driver;
    }

    protected void scrollToElement(WebElement webElement) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView(false)", webElement);
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
}
