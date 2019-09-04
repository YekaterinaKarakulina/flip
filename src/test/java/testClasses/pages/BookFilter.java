package testClasses.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookFilter extends AbstractPage {
    @FindBy(xpath = "//input[@id='filter-field-i302-f']")//div[@class='filter-left']//input[@placeholder='2000']
    private WebElement publicationYearRangeFirstValue;


    @FindBy(xpath = "//input[@id='filter-field-i302-t']")//div[@class='filter-left']//input[@placeholder='2098']
    private WebElement getPublicationYearRangeLastValue;

    @FindBy(xpath = "//li[@data-filter-range-max='2098']//button[text()='Применить']")
    private WebElement publicationYearFilterApplyButton;

    private static final By AUTHORS_LIST_LOCATOR = By.xpath("//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-default,'true')]");
    private static String xpathAuthorToClick = "//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-name,'%s')]//*[contains(@class,'checkbox')]";
    private List<String> clickedAuthorsList;

    public List<String> getSelectedAuthorsList() {
        List<WebElement> list = getDriver().findElements(By.xpath("//div[contains(@id,'filter-header')]//span"));
        return list.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    private List<String> getAuthorsList() {
        List<WebElement> authorsElements = getDriver().findElements(AUTHORS_LIST_LOCATOR);
        return authorsElements.stream().map(item -> item.getAttribute("data-list-found-name")).collect(Collectors.toList());
    }

    public SectionPage clickRandomAuthor(int amountOfAuthors) {
        clickedAuthorsList = new ArrayList<>();
        for (int i = 0; i < amountOfAuthors; i++) {
            List<String> authorsList = getAuthorsList().stream().filter(item -> !clickedAuthorsList.contains(item)).collect(Collectors.toList());
            Collections.shuffle(authorsList);
            String authorToClick = authorsList.get(0);
            getDriver().findElement(By.xpath(String.format(xpathAuthorToClick, authorToClick))).click();
            clickedAuthorsList.add(authorsList.get(0));
            waitUntilSearchIsReady();
            waitUntilElementHasText(By.xpath("//div[contains(@class,'filters')]"), authorToClick);
        }
        return new SectionPage(getDriver());
    }

    public BookFilter setPublicationYearFirstValue(String year) {
        publicationYearRangeFirstValue.sendKeys(year);
        return new BookFilter(getDriver());
    }

    public BookFilter setPublicationYearLastValue(String year) {
        getPublicationYearRangeLastValue.sendKeys(year);
        return new BookFilter(getDriver());
    }

    public SectionPage applyPublicationYearFilter() {
        publicationYearFilterApplyButton.click();
        return new SectionPage(getDriver());
    }

    BookFilter(WebDriver driver) {
        super(driver);
    }
}
