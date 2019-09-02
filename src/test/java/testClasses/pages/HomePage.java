package testClasses.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends AbstractPage {
    private static final By BOOKS_SECTION_LOCATOR = By.xpath("//a[contains(@href,'1') and contains(text(), 'Книги')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get("https://flip.kz");
        return this;
    }

    public BooksPage chooseBookSection() {
        driver.findElement(BOOKS_SECTION_LOCATOR).click();
        return new BooksPage(driver);
    }
}
