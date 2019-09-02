package testClasses.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

public class RandomSearchResultsPage extends AbstractPage {
    private static final By BOOK_ITEM_LOCATOR = By.xpath("//div[@class='placeholder']//a[@class='title']");

    public RandomSearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public RandomBookPage clickOnRandomBookCard() {
        List<WebElement> booksList = driver.findElements(BOOK_ITEM_LOCATOR);
        int randomBook = new Random().nextInt(booksList.size());
        WebElement bookItem = booksList.get(randomBook);
        waitUntilClickable(bookItem);
        scrollToElement(bookItem);
        waitUntilClickable(bookItem);
        waitUntilSearchIsReady();
        bookItem.click();
        return new RandomBookPage(driver);
    }
}
