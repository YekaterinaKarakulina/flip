package selenide.tests.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.page;

public class SearchCriteria extends BasePage {

    private static final String publicationYearRangeFirstValue = "//p[contains(text(),'Год издания')]/following::*[contains(text(), 'от')]//input";
    private static final String publicationYearRangeLastValue = "//p[contains(text(),'Год издания')]/following::*[contains(text(), 'до')]//input";
    private static final String publicationYearRangeFirstValueSlider = "//p[contains(text(),'Год издания')]/following::a[contains(@class,'slider')][1]";
    private static final String publicationYearRangeLastValueSlider = "//p[contains(text(),'Год издания')]/following::a[contains(@class,'slider')][2]";
    private static final String publicationYearFilterApplyButton = "//p[contains(text(),'Год издания')]/following::*[contains(text(), 'Применить')]";
    private static final String authorsList = "//div[@data-filter-field-list-type='peoples']//li[@data-list-found-name]";
    private static final String currentFilter = "//div[contains(@class,'filters')]";

    private static String xpathAuthorToClick = "//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-name,'%s')]//*[contains(@class,'checkbox')]";
    private List<String> clickedAuthorsList;

    public List<String> getSelectedAuthorsList() {
        return clickedAuthorsList;
    }

/*
    public int getExpectedPublicationYearFirstValue() {
        waitForElementEnabled(publicationYearRangeFirstValue);
        return Integer.parseInt(publicationYearRangeFirstValue.getAttribute("value"));
    }

    public int getExpectedPublicationYearLastValue() {
        waitForElementEnabled(publicationYearRangeLastValue);
        return Integer.parseInt(publicationYearRangeLastValue.getAttribute("value"));
    }

    public SectionPage setPublicationYearFirstValue() {
        int randomPosition = RandomNumbersUtils.getRandomNumber(0, 15);
        scrollToElement(publicationYearRangeFirstValueSlider);
        dragAndDropWebElementToPosition(publicationYearRangeFirstValueSlider, randomPosition, 0);
        scrollToElement(publicationYearFilterApplyButton);
        clickToWebElement(publicationYearFilterApplyButton);
        return new SectionPage(getDriver());
    }

    public SectionPage setPublicationYearLastValue() {
        int randomPosition = RandomNumbersUtils.getRandomNumber(-200, -100);
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
    }*/

    public SectionPage clickRandomAuthor(int amountOfAuthors) {
        clickedAuthorsList = new ArrayList<>();
        for (int i = 0; i < amountOfAuthors; i++) {
            List<String> authorsList = getAuthorsList().stream().filter(item -> !clickedAuthorsList.contains(item)).collect(Collectors.toList());
            Collections.shuffle(authorsList);
            ;
            String authorToClick = authorsList.get(0);
            SelenideElement authorToClickElement = $(By.xpath(String.format(xpathAuthorToClick, authorToClick)));
            authorToClickElement.scrollTo();
            clickToSelenideElement(authorToClickElement);
            clickedAuthorsList.add(authorToClick);
            waitUntilSearchIsReady();
            $(By.xpath(currentFilter)).waitUntil(Condition.text(authorToClick), getTimeToWait());
        }
        return page(SectionPage.class);
    }


    private List<String> getAuthorsList() {
        return $$(By.xpath(authorsList)).stream().map(item -> item.getAttribute("data-list-found-name")).collect(Collectors.toList());
    }


}
