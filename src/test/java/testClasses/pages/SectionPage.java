package testClasses.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;


public class SectionPage extends AbstractPage {
    private String xpathRandomPage = "//a[@data-page='%s']";
    private static final By BOOK_ITEM_LOCATOR = By.xpath("//div[@class='placeholder']//a[@class='title']");

    private static final By AMOUNT_OF_PAGES_LOCATOR = By.xpath("//a[@data-page]");

    public SectionPage moveToRandomPage() {
        waitUntilSearchIsReady();
        int actualNumber = getDriver().findElements(AMOUNT_OF_PAGES_LOCATOR).size();
        int randomNumber = new Random().ints(1, actualNumber + 1).findFirst().getAsInt();
        if (randomNumber != 1) {
            waitUntilSearchIsReady();
            scrollToTheEndOfPage();
            waitUntilClickable(getDriver().findElement(By.xpath(String.format(xpathRandomPage, randomNumber))));
            scrollToTheEndOfPage();
            getDriver().findElement(By.xpath(String.format(xpathRandomPage, randomNumber))).click();
            scrollToTheEndOfPage();
            waitUntilSearchIsReady();
            waitUntilElementHasText(By.xpath("//td[contains(@class,'pages')]//span"), Integer.toString(randomNumber));
        }
        return new SectionPage(getDriver());
    }

    public ItemPage clickOnRandomBookCard() {
        List<WebElement> booksList = getDriver().findElements(BOOK_ITEM_LOCATOR);
        int randomBook = new Random().nextInt(booksList.size());
        WebElement bookItem = booksList.get(randomBook);
        waitUntilClickable(bookItem);
        scrollToElement(bookItem);
        waitUntilClickable(bookItem);
        waitUntilSearchIsReady();
        bookItem.click();
        return new ItemPage(getDriver());
    }

    public SectionPage(WebDriver driver) {
        super(driver);
    }

    public BookFilter getBookFilter() {
        return new BookFilter(getDriver());
    }
}
