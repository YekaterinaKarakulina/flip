package selenide.tests.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenide.utils.RandomNumbersUtils;

import java.util.List;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.page;

public class SectionPage extends BasePage {

    private static final String booksList = "//div[@class='placeholder']//a[@class='title']";

    public List<SelenideElement> getPages() {
        return $$(By.xpath("//table[@class='pages']//td/*[text()>0]"));
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
        return page(SectionPage.class);
    }

    public ItemPage clickOnRandomBookCard() {
        int actualSize = $$(By.xpath(booksList)).size();
        int randomBook = RandomNumbersUtils.getRandomNumber(0, actualSize - 1);
        SelenideElement bookItem = $$(By.xpath(booksList)).get(randomBook);
        bookItem.scrollTo();
        clickToSelenideElement(bookItem);
        List<WebElement> currentAuthorsList = WebDriverRunner.getWebDriver().findElements(By.xpath(getBookAuthorsList()));
        waitForListWebElementsVisible(currentAuthorsList);
        return page(ItemPage.class);
    }
}





