package testClasses.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;

public class SectionPage extends BasePage {
    @FindBy(xpath = "//div[@class='placeholder']//a[@class='title']")
    private List<WebElement> booksList;

    @FindBy(xpath = "//table[@class='pages']//a[text()>0]")
    private List<WebElement> pages;

    @FindBy(xpath = "//td[contains(@class,'pages')]//span")
    private WebElement currentPage;

    public SectionPage(WebDriver driver) {
        super(driver);
    }

    public BookFilter getBookFilter() {
        return new BookFilter(getDriver());
    }

    public SectionPage moveToRandomPage() {
        waitUntilSearchIsReady();
        int actualNumber = pages.size();
        int randomNumber = new Random().ints(1, actualNumber + 2).findFirst().getAsInt();
        if (randomNumber != 1) {
            randomNumber = new Random().ints(0, actualNumber).findFirst().getAsInt();
            waitUntilSearchIsReady();
            scrollToTheEndOfPage();
            waitForElementEnabled(pages.get(randomNumber));
            scrollToTheEndOfPage();
            pages.get(randomNumber).click();
            waitUntilSearchIsReady();
            waitUntilElementHasText(currentPage, Integer.toString(randomNumber + 2));
        }
        return new SectionPage(getDriver());
    }

    public ItemPage clickOnRandomBookCard() {
        int randomBook = new Random().nextInt(booksList.size());
        WebElement bookItem = booksList.get(randomBook);
        waitForElementEnabled(bookItem);
        scrollToElement(bookItem);
        waitForElementEnabled(bookItem);
        waitUntilSearchIsReady();
        bookItem.click();
        return new ItemPage(getDriver());
    }
}