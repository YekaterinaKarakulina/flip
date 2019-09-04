package testClasses.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;

public class SectionPage extends BasePage {
    private String xpathRandomPage = "//a[@data-page='%s']";

    @FindBy(xpath = "//div[@class='placeholder']//a[@class='title']")
    private List<WebElement> booksList;

    @FindBy(xpath = "//a[@data-page]")
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
        System.out.println("act number " + actualNumber);
        int randomNumber = new Random().ints(1, actualNumber + 1).findFirst().getAsInt();
        System.out.println("rand number " + randomNumber);
        if (randomNumber != 1) {
            waitUntilSearchIsReady();
            scrollToTheEndOfPage();
            waitForElementEnabled(getDriver().findElement(By.xpath(String.format(xpathRandomPage, randomNumber))));
            scrollToTheEndOfPage();
            getDriver().findElement(By.xpath(String.format(xpathRandomPage, randomNumber))).click();
            scrollToTheEndOfPage();
            waitUntilSearchIsReady();
            waitUntilElementHasText(currentPage, Integer.toString(randomNumber));
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
