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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class BasePage {

    private static final Duration MAX = Duration.ofSeconds(60);
    private static final Duration POLLING = Duration.ofSeconds(1);

    private static final long timeToWait = 1000;

    private static final String pageContent = ".//div[@id='content']";
    private static final String bookAuthorsList = "//table[@id='prod']//*[contains(@href,'people')]";

    public static String getBookAuthorsList() {
        return bookAuthorsList;
    }

    public static long getTimeToWait() {
        return timeToWait;
    }

    protected void clickToSelenideElement(SelenideElement element) {
        element.waitUntil(Condition.enabled, timeToWait);
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
        $(By.xpath(pageContent)).waitUntil(Condition.enabled, timeToWait);
    }

    protected void waitForListWebElementsVisible(List<WebElement> webElements) {
        FluentWait<WebDriver> wait = new FluentWait<>(WebDriverRunner.getWebDriver())
                .withTimeout(MAX)
                .pollingEvery(POLLING);
        wait.ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(ExpectedConditions.visibilityOfAllElements(webElements));
    }

}
