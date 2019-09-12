package tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SearchCriteria extends BasePage {
    private static String xpathAuthorToClick = "//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-name,'%s')]//*[contains(@class,'checkbox')]";
    private List<String> clickedAuthorsList;

    @FindBy(xpath = "//p[contains(text(),'Год издания')]/following::*[contains(text(), 'от')]//input")
    private WebElement publicationYearRangeFirstValue;

    @FindBy(xpath = "//p[contains(text(),'Год издания')]/following::*[contains(text(), 'до')]//input")
    private WebElement publicationYearRangeLastValue;

    @FindBy(xpath = "//p[contains(text(),'Год издания')]/following::a[contains(@class,'slider')][1]")
    private WebElement publicationYearRangeFirstValueSlider;

    @FindBy(xpath = "//p[contains(text(),'Год издания')]/following::a[contains(@class,'slider')][2]")
    private WebElement publicationYearRangeLastValueSlider;

    @FindBy(xpath = "//p[contains(text(),'Год издания')]/following::*[contains(text(), 'Применить')]")
    private WebElement publicationYearFilterApplyButton;

    @FindBy(xpath = "//div[@data-filter-field-list-type='peoples']//li[@data-list-found-name]")
    private List<WebElement> authorsList;

    @FindBy(xpath = "//div[contains(@class,'filters')]")
    private WebElement currentFilter;

    public SearchCriteria(WebDriver driver) {
        super(driver);
    }

    public List<String> getSelectedAuthorsList() {
        return clickedAuthorsList;
    }

    public int getExpectedPublicationYearFirstValue() {
        return Integer.parseInt(publicationYearRangeFirstValue.getAttribute("value"));
    }

    public int getExpectedPublicationYearLastValue() {
        return Integer.parseInt(publicationYearRangeLastValue.getAttribute("value"));
    }

    public SectionPage setPublicationYearFirstValue() {
        int randomPosition = new Random().ints(0, 15).findFirst().getAsInt();
        scrollToElement(publicationYearRangeFirstValueSlider);
        dragAndDropWebElementToPosition(publicationYearRangeFirstValueSlider, randomPosition, 0);
        scrollToElement(publicationYearFilterApplyButton);
        clickToWebElement(publicationYearFilterApplyButton);
        return new SectionPage(getDriver());
    }

    public SectionPage setPublicationYearLastValue() {
        int randomPosition = new Random().ints(-200, -100).findFirst().getAsInt();
        scrollToElement(publicationYearRangeLastValueSlider);
        dragAndDropWebElementToPosition(publicationYearRangeLastValueSlider, randomPosition, 0);
        scrollToElement(publicationYearFilterApplyButton);
        clickToWebElement(publicationYearFilterApplyButton);
        return new SectionPage(getDriver());
    }

    public SectionPage setPublicationYearFilter(String yearFrom, String yearTo) {
        scrollToElement(publicationYearRangeFirstValue);
        sendKeysToWebElement(publicationYearRangeFirstValue, yearFrom);
        sendKeys(Keys.TAB);
        sendKeysToWebElement(publicationYearRangeLastValue, yearTo);
        sendKeys(Keys.RETURN);
        return new SectionPage(getDriver());
    }

    public SectionPage clickRandomAuthor(int amountOfAuthors) {
        clickedAuthorsList = new ArrayList<>();
        for (int i = 0; i < amountOfAuthors; i++) {
            List<String> authorsList = getAuthorsList().stream().filter(item -> !clickedAuthorsList.contains(item)).collect(Collectors.toList());
            Collections.shuffle(authorsList);
            String authorToClick = authorsList.get(0);
            WebElement authorToClickElement = getDriver().findElement(By.xpath(String.format(xpathAuthorToClick, authorToClick)));
            scrollToElement(authorToClickElement);
            clickToWebElement(authorToClickElement);
            clickedAuthorsList.add(authorToClick);
            waitUntilSearchIsReady();
            waitUntilElementHasText(currentFilter, authorToClick);
        }
        return new SectionPage(getDriver());
    }

    private List<String> getAuthorsList() {
        return authorsList.stream().map(item -> item.getAttribute("data-list-found-name")).collect(Collectors.toList());
    }
}
