package testClasses.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class AbstractPage {
    private Duration max = Duration.ofSeconds(60);
    private Duration polling = Duration.ofSeconds(1);
    protected WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
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
                .until(ExpectedConditions.attributeToBe(driver.findElement(By.xpath(".//div[@id='content']")),
                        "style", "opacity: 1;"));
    }

    protected void waitUntilElementHasText(By by, String text) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(max)
                .pollingEvery(polling);
        wait.ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(ExpectedConditions.textToBePresentInElement(driver.findElement(by), text));
    }

    protected void scrollToElement(WebElement webElement) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView(false)", webElement);
    }

    protected void scrollToTheEndOfPage() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

}
