package tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;

public class SectionPage extends BasePage {

    @FindBy(xpath = "//div[@class='placeholder']//a[@class='title']")
    private List<WebElement> booksList;

    @FindBy(xpath = "//table[@class='pages']//td/*[text()>0]")
    private List<WebElement> pages;

    @FindBy(xpath = "//td[contains(@class,'pages')]//span")
    private WebElement currentPage;

    public SectionPage(WebDriver driver) {
        super(driver);
    }

    public SearchCriteria getBookFilter() {
        return new SearchCriteria(getDriver());
    }

    public SectionPage moveToRandomPage() {
        waitUntilSearchIsReady();
        int actualNumber = pages.size();
        int randomNumber = new Random().ints(1, actualNumber + 1).findFirst().getAsInt();
        waitUntilSearchIsReady();
        scrollToTheEndOfPage();
        WebElement pageToClick = pages.stream().filter(item -> item.getText().equals(Integer.toString(randomNumber))).findFirst().get();
        scrollToTheEndOfPage();
        clickToWebElement(pageToClick);
        waitForElementEnabled(pageToClick);
        waitUntilSearchIsReady();
        waitUntilElementHasText(currentPage, Integer.toString(randomNumber));
        return new SectionPage(getDriver());
    }

    public ItemPage clickOnRandomBookCard() {
        int randomBook = new Random().nextInt(booksList.size());
        WebElement bookItem = booksList.get(randomBook);
        scrollToElement(bookItem);
        clickToWebElement(bookItem);
        return new ItemPage(getDriver());
    }

}
