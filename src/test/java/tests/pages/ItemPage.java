package tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ItemPage extends BasePage {

    @FindBy(xpath = "//table[@id='prod']//*[contains(@href,'people')]")
    private WebElement bookAuthor;

    @FindBy(xpath = "//table[@id='prod']//span[@itemprop='name']")
    private WebElement bookName;

    @FindBy(xpath = "//div[@class='description-table']//div[a[@href]]")
    private WebElement bookPublicationYear;

    public ItemPage(WebDriver driver) {
        super(driver);
    }

    public String getBookAuthor() {
        return bookAuthor.getText();
    }

    public String getBookName() {
        return bookName.getText();
    }

    public int getBookPublicationYear() {
        String expectedPublicationYearStr = bookPublicationYear.getText();
        return Integer.parseInt(expectedPublicationYearStr.substring(expectedPublicationYearStr.lastIndexOf(',') + 1).replaceAll("\\D+", ""));
    }

}
