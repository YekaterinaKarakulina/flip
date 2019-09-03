package testClasses.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PublicationYearSearchComponent extends AbstractPage {

    @FindBy(xpath = "//div[@class='filter-left']//input[@placeholder='2000']")
    private WebElement publicationYearRangeFirstValue;

    @FindBy(xpath = "//div[@class='filter-left']//input[@placeholder='2098']")
    private WebElement getPublicationYearRangeLastValue;

    @FindBy(xpath = "//li[@data-filter-range-max='2098']//button[text()='Применить']")
    private WebElement publicationYearFilterApplyButton;


    public SectionPage setPublicationYearFirstValue(String year) {
       publicationYearRangeFirstValue.sendKeys(year);
        return new SectionPage(getDriver());
    }

    public SectionPage setPublicationYearLastValue(String year) {
        getPublicationYearRangeLastValue.sendKeys(year);
        return new SectionPage(getDriver());
    }

    public SectionPage applyPublicationYearFilter() {
        publicationYearFilterApplyButton.click();
        return new SectionPage(getDriver());
    }

    PublicationYearSearchComponent(WebDriver driver) {
        super(driver);
    }
}
