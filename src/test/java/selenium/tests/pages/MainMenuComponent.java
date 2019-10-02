package selenium.tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainMenuComponent extends BasePage {

    @FindBy(xpath = "//p[contains(text(),\"Каталог\")]/ancestor::div[contains(@class,'menu')]")
    private WebElement menu;

    @FindBy(xpath = "//ul[contains(@class,'sub-1')]/li/a[contains(text(), 'Книги')]")
    private WebElement booksSection;

    @FindBy(xpath = "//a[@data-filter-field-sections-id='44']")
    private WebElement imaginativeLiteratureSection;

    @FindBy(xpath = "//p[text()='Фильтр']")
    private WebElement filter;

    public MainMenuComponent(WebDriver driver) {
        super(driver);
    }

    public MainMenuComponent clickBookSection() {
        moveToWebElement(menu);
        clickToWebElement(booksSection);
        waitForElementEnabled(filter);
        return new MainMenuComponent(getDriver());
    }

    public SearchCriteria clickImaginativeLiteratureSection() {
        clickToWebElement(imaginativeLiteratureSection);
        waitUntilElementsAttributeHasText(imaginativeLiteratureSection, "class", "active");
        return new SearchCriteria(getDriver());
    }

}
