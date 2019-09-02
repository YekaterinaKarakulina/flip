package testClasses.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BooksPage extends AbstractPage {
    private static final By IMAGINATIVE_LITERATURE_SECTION_LOCATOR = By.xpath("//a[@data-filter-field-sections-id='44']");

    public BooksPage(WebDriver driver) {
        super(driver);
    }

    public ImaginativeLiteraturePage chooseImaginativeLiteratureSection(){
        driver.findElement(IMAGINATIVE_LITERATURE_SECTION_LOCATOR).click();
        return new ImaginativeLiteraturePage(driver);
    }



}
