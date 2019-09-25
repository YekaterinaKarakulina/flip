package selenium.tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class ItemPage extends BasePage {

    @FindBy(xpath = "//table[@id='prod']//span[@itemprop='name']")
    private WebElement bookName;

    @FindBy(xpath = "//div[@class='description-table']//div[a[@href]]")
    private WebElement bookPublicationYear;

    public ItemPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getBookAuthors() {
        waitForListWebElementsVisible(getBookAuthorsList());
        return getBookAuthorsList().stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public String getBookName() {
        waitForElementEnabled(bookName);
        return bookName.getText();
    }

    public int getBookPublicationYear() {
        waitForElementEnabled(bookPublicationYear);
        String actualPublicationYearStr = getStringByRegexMatcher(bookPublicationYear.getText());
        return Integer.parseInt(actualPublicationYearStr);
    }

}
