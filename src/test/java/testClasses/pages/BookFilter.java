package testClasses.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookFilter extends BasePage {
    private static String xpathAuthorToClick = "//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-name,'%s')]//*[contains(@class,'checkbox')]";
    private List<String> clickedAuthorsList;

    @FindBy(xpath = "//p[contains(text(),'Год издания')]/following::*[contains(text(), 'от')]//input")
    private WebElement publicationYearRangeFirstValue;

    @FindBy(xpath = "//p[contains(text(),'Год издания')]/following::*[contains(text(), 'до')]//input")
    private WebElement getPublicationYearRangeLastValue;

    @FindBy(xpath = "//p[contains(text(),'Год издания')]/following::*[contains(text(), 'Применить')]")
    private WebElement publicationYearFilterApplyButton;

    @FindBy(xpath = "//div[@data-filter-field-list-type='peoples']//li[@data-list-found-name]")
    private List<WebElement> authorsList;

    @FindBy(xpath = "//div[contains(@class,'filters')]")
    private WebElement currentFilter;

    public BookFilter(WebDriver driver) {
        super(driver);
    }

    public List<String> getSelectedAuthorsList() {
        return clickedAuthorsList;
    }

    private List<String> getAuthorsList() {
        return authorsList.stream().map(item -> item.getAttribute("data-list-found-name")).collect(Collectors.toList());
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

    public SectionPage clickRandomAuthor(int amountOfAuthors) {
        clickedAuthorsList = new ArrayList<>();
        for (int i = 0; i < amountOfAuthors; i++) {
            List<String> authorsList = getAuthorsList().stream().filter(item -> !clickedAuthorsList.contains(item)).collect(Collectors.toList());
            Collections.shuffle(authorsList);
            String authorToClick = authorsList.get(0);
            getDriver().findElement(By.xpath(String.format(xpathAuthorToClick, authorToClick))).click();
            clickedAuthorsList.add(authorToClick);
            waitUntilSearchIsReady();
            waitUntilElementHasText(currentFilter, authorToClick);
        }
        return new SectionPage(getDriver());
    }
}
