package testClasses.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RandomBookPage extends AbstractPage{

    private static final By ACTUAL_BOOK_AUTHOR_LOCATOR = By.xpath( "//table[@id='prod']//*[contains(@href,'people')]");
    private static final By BOOK_NAME_LOCATOR = By.xpath("//table[@id='prod']//span[@itemprop='name']");

    public RandomBookPage(WebDriver driver) {
        super(driver);
    }

    public String getBookAuthor()
    {
        return driver.findElement(ACTUAL_BOOK_AUTHOR_LOCATOR).getText();
    }

    public String getBookName()
    {
        return driver.findElement(BOOK_NAME_LOCATOR).getText();
    }


}
