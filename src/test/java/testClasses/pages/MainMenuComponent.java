package testClasses.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainMenuComponent extends AbstractPage {
    @FindBy(xpath = "//a[contains(@href,'1') and contains(text(), 'Книги')]")
    private WebElement booksSection;

    @FindBy(xpath = "//a[@data-filter-field-sections-id='44']")
    private WebElement imaginativeLiteratureSection;

    MainMenuComponent(WebDriver driver) {
        super(driver);
    }

    public SectionPage chooseBookSection() {
        booksSection.click();
        return new SectionPage(getDriver());
    }

    public SectionPage chooseImaginativeLiteratureSection() {
      imaginativeLiteratureSection.click();
        return new SectionPage(getDriver());
    }

}
