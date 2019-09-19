package selenium.tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.utils.RandomNumbersUtils;

import java.util.List;

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

    public SearchCriteria getSearchCriteria() {
        return new SearchCriteria(getDriver());
    }

    public SectionPage moveToRandomPage() {
        waitUntilSearchIsReady();
        int actualNumber = pages.size();
        int randomNumber = RandomNumbersUtils.getRandomNumber(1, actualNumber);
        waitUntilSearchIsReady();
        scrollToTheEndOfPage();
        WebElement pageToClick = pages.stream().filter(item -> item.getText().equals(Integer.toString(randomNumber))).findFirst().get();
        scrollToTheEndOfPage();
        clickToWebElement(pageToClick);
        waitUntilSearchIsReady();
        waitUntilElementHasText(currentPage, Integer.toString(randomNumber));
        return new SectionPage(getDriver());
    }

    public ItemPage clickOnRandomBookCard() {
        int randomBook = RandomNumbersUtils.getRandomNumber(0, booksList.size() - 1);
        WebElement bookItem = booksList.get(randomBook);
        scrollToElement(bookItem);
        clickToWebElement(bookItem);
        waitForListWebElementsVisible(getBookAuthorsList());
        return new ItemPage(getDriver());
    }

}
