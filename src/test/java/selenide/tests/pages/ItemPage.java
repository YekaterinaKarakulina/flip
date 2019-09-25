package selenide.tests.pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ItemPage extends BasePage {

    private static final By BOOK_NAME = By.xpath("//table[@id='prod']//span[@itemprop='name']");
    private static final By BOOK_PUBLICATION_YEAR = By.xpath("//div[@class='description-table']//div[a[@href]]");

    public List<String> getBookAuthors() {
        return $$(getBookAuthorsList()).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public String getBookName() {
        $(BOOK_NAME).shouldBe(Condition.enabled);
        return $(BOOK_NAME).getText();
    }

    public String getBookPublicationYear() {
        $(BOOK_PUBLICATION_YEAR).scrollTo().shouldBe(Condition.enabled);
        String actualPublicationYearStr = $(BOOK_PUBLICATION_YEAR).getText().replaceAll("(.+,)","").replaceAll("\\D+", "");
        return actualPublicationYearStr;
    }

}
