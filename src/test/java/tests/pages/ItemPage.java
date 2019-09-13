package tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class ItemPage extends BasePage {

    @FindBy(xpath = "//table[@id='prod']//*[contains(@href,'people')]")
    private static List<WebElement> bookAuthorsList;

    @FindBy(xpath = "//table[@id='prod']//span[@itemprop='name']")
    private WebElement bookName;

    @FindBy(xpath = "//div[@class='description-table']//div[a[@href]]")
    private WebElement bookPublicationYear;

    public ItemPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getBookAuthors() {
        return bookAuthorsList.stream().map(item -> item.getText()).collect(Collectors.toList());
    }

    public String getBookName() {
        return bookName.getText();
    }

    public int getBookPublicationYear() {
        String expectedPublicationYearStr = bookPublicationYear.getText();
        return Integer.parseInt(expectedPublicationYearStr.substring(expectedPublicationYearStr.lastIndexOf(',') + 1).replaceAll("\\D+", ""));
    }

}
