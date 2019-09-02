package testClasses.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RandomBookPage extends AbstractPage {

    private static final By ACTUAL_BOOK_AUTHOR_LOCATOR = By.xpath("//table[@id='prod']//*[contains(@href,'people')]");
    private static final By BOOK_NAME_LOCATOR = By.xpath("//table[@id='prod']//span[@itemprop='name']");
    private static final By BOOK_PUBLICATION_YEAR_LOCATOR = By.xpath("//div[@class='description-table']//div[a[@href]]");

    public RandomBookPage(WebDriver driver) {
        super(driver);
    }

    public String getBookAuthor() {
        return driver.findElement(ACTUAL_BOOK_AUTHOR_LOCATOR).getText();
    }

    public String getBookName() {
        return driver.findElement(BOOK_NAME_LOCATOR).getText();
    }

    public int getBookPublicationYear() {
        String expectedPublicationYearStr = driver.findElement(BOOK_PUBLICATION_YEAR_LOCATOR).getText();
        return Integer.parseInt(expectedPublicationYearStr.substring(expectedPublicationYearStr.lastIndexOf(',') + 1).replaceAll("\\D+", ""));
    }
}
