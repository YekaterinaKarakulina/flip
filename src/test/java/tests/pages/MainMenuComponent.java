package tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        moveToWebElement(menu);
        clickToWebElement(booksSection);
        return new MainMenuComponent(getDriver());
    }

    public SearchCriteria clickImaginativeLiteratureSection() {
        clickToWebElement(imaginativeLiteratureSection);
        return new SearchCriteria(getDriver());
    }
}
