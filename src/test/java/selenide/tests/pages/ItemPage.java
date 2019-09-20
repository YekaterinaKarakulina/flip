package selenide.tests.pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ItemPage extends BasePage {

    private static final String BOOK_NAME = "//table[@id='prod']//span[@itemprop='name']";
    private static final String BOOK_PUBLICATION_YEAR = "//div[@class='description-table']//div[a[@href]]";

    public List<String> getBookAuthors() {
        return $$(By.xpath(getBookAuthorsList())).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public String getBookName() {
        $(By.xpath(BOOK_NAME)).shouldHave(Condition.enabled);
        return $(By.xpath(BOOK_NAME)).getText();
    }

    public int getBookPublicationYear() {
        $(By.xpath(BOOK_PUBLICATION_YEAR)).scrollTo().shouldHave(Condition.enabled);
        String expectedPublicationYearStr = $(By.xpath(BOOK_PUBLICATION_YEAR)).getText();
        return Integer.parseInt(expectedPublicationYearStr.substring(expectedPublicationYearStr.lastIndexOf(',') + 1).replaceAll("\\D+", ""));
    }

}