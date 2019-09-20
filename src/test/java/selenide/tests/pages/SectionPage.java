package selenide.tests.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenide.utils.RandomNumbersUtils;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.page;

public class SectionPage extends BasePage {

    private static final String CURRENT_PAGE = "//td[contains(@class,'pages')]//span";
    private static final String BOOKS_LIST = "//div[@class='placeholder']//a[@class='title']";

    public List<SelenideElement> getPages() {
        return $$(By.xpath("//table[@class='pages']//td/*[text()>0]"));
    }

    public SearchCriteria getSearchCriteria() {
        return page(SearchCriteria.class);
    }

    public SectionPage moveToRandomPage() {
        waitUntilSearchIsReady();
        int actualNumber = getPages().size();
        int randomNumber = RandomNumbersUtils.getRandomNumber(1, actualNumber);
        waitUntilSearchIsReady();
        scrollToTheEndOfPage();
        SelenideElement pageToClick = getPages().stream().filter(item -> item.getText().equals(Integer.toString(randomNumber))).findFirst().get();
        scrollToTheEndOfPage();
        clickToSelenideElement(pageToClick);
        waitUntilSearchIsReady();
        $(By.xpath(CURRENT_PAGE)).scrollTo().waitUntil(Condition.enabled, getTimeToWait());
        $(By.xpath(CURRENT_PAGE)).waitUntil(Condition.text(Integer.toString(randomNumber)), getTimeToWait());
        return page(SectionPage.class);
    }

    public ItemPage clickOnRandomBookCard() {
        int actualSize = $$(By.xpath(BOOKS_LIST)).size();
        int randomBook = RandomNumbersUtils.getRandomNumber(0, actualSize - 1);
        SelenideElement bookItem = $$(By.xpath(BOOKS_LIST)).get(randomBook);
        bookItem.scrollTo();
        clickToSelenideElement(bookItem);
        List<WebElement> currentAuthorsList = WebDriverRunner.getWebDriver().findElements(By.xpath(getBookAuthorsList()));
        waitForListWebElementsVisible(currentAuthorsList);
        return page(ItemPage.class);
    }

}




