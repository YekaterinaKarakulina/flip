package selenide.tests.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class BasePage {

    private static final Duration MAX = Duration.ofSeconds(60);
    private static final Duration POLLING = Duration.ofSeconds(1);

    private static final long TIME_TO_WAIT = 100000;


   private static final By PAGE_CONTENT = By.xpath(".//div[@id='content']");
    private static final By BOOK_AUTHORS_LIST = By.xpath("//table[@id='prod']//*[contains(@href,'people')]");

   public static By getBookAuthorsList() {
        return BOOK_AUTHORS_LIST;
    }

    public static long getTimeToWait() {
        return TIME_TO_WAIT;
    }

    protected void dragAndDropWebElementToPosition(SelenideElement element, int x, int y) {
        element.waitUntil(Condition.enabled, getTimeToWait());
        new Actions(WebDriverRunner.getWebDriver()).dragAndDropBy(element, x, y).build().perform();
    }

    protected void clickToSelenideElement(SelenideElement element) {
        element.waitUntil(Condition.enabled, TIME_TO_WAIT);
        highlightElement(element);
        element.click();
    }

    protected void highlightElement(WebElement element) {
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].style.border='3px solid red'", element);
    }

    protected void scrollToTheEndOfPage() {
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    protected void waitUntilSearchIsReady() {
        $(PAGE_CONTENT).waitUntil(Condition.enabled, TIME_TO_WAIT);
    }

    protected void waitForListWebElementsVisible(List<WebElement> webElements) {
        FluentWait<WebDriver> wait = new FluentWait<>(WebDriverRunner.getWebDriver())
                .withTimeout(MAX)
                .pollingEvery(POLLING);
        wait.ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(ExpectedConditions.visibilityOfAllElements(webElements));
    }

}
