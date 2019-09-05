package testClasses.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class MainMenuComponent extends BasePage {
    @FindBy(xpath = "//p[contains(text(),'Каталог')]/ancestor::div[contains(@class,'menu')]")
    private WebElement menu;

    @FindBy(xpath = "//a[contains(@href,'1') and contains(text(), 'Книги')]")
    private WebElement booksSection;

    @FindBy(xpath = "//a[@data-filter-field-sections-id='44']")
    private WebElement imaginativeLiteratureSection;

    public MainMenuComponent(WebDriver driver) {
        super(driver);
    }

    public MainMenuComponent clickBookSection() {
        new Actions(getDriver()).moveToElement(menu).build().perform();
        new Actions(getDriver()).click(booksSection).build().perform();
        return new MainMenuComponent(getDriver());
    }

    public BookFilter clickImaginativeLiteratureSection() {
        imaginativeLiteratureSection.click();
        return new BookFilter(getDriver());
    }
}
