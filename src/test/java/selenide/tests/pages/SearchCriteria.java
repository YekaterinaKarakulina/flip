package selenide.tests.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import selenide.utils.RandomNumbersUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.page;

public class SearchCriteria extends BasePage {

    private static final String CURRENT_FILTER = "//div[contains(@class,'filters')]";
    private static final String CURRENT_FILTER_YEAR = "//div[contains(@class,'filters')]//*[contains(@title, 'Год')]/span";
    private static final String PUBLICATION_YEAR_RANGE_FIRST_VALUE = "//p[contains(text(),'Год издания')]/following::*[contains(text(), 'от')]//input";
    private static final String PUBLICATION_YEAR_RANGE_LAST_VALUE = "//p[contains(text(),'Год издания')]/following::*[contains(text(), 'до')]//input";
    private static final String PUBLICATION_YEAR_RANGE_FIRST_VALUE_SLIDER = "//p[contains(text(),'Год издания')]/following::a[contains(@class,'slider')][1]";
    private static final String PUBLICATION_YEAR_RANGE_LAST_VALUE_SLIDER = "//p[contains(text(),'Год издания')]/following::a[contains(@class,'slider')][2]";
    private static final String PUBLICATION_YEAR_FILTER_APPLY_BUTTON = "//p[contains(text(),'Год издания')]/following::*[contains(text(), 'Применить')]";
    private static final String AUTHORS_LIST = "//div[@data-filter-field-list-type='peoples']//li[@data-list-found-name]";

    private static String xpathAuthorToClick = "//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-name,'%s')]//*[contains(@class,'checkbox')]";
    private List<String> clickedAuthorsList;

    public List<String> getSelectedAuthorsList() {
        return clickedAuthorsList;
    }


    public int getExpectedPublicationYearFirstValue() {
        $(By.xpath(PUBLICATION_YEAR_RANGE_FIRST_VALUE)).waitUntil(Condition.enabled, getTimeToWait());
        return Integer.parseInt($(By.xpath(PUBLICATION_YEAR_RANGE_FIRST_VALUE)).getAttribute("value"));
    }

    public int getExpectedPublicationYearLastValue() {
        $(By.xpath(PUBLICATION_YEAR_RANGE_LAST_VALUE)).waitUntil(Condition.enabled, getTimeToWait());
        return Integer.parseInt($(By.xpath(PUBLICATION_YEAR_RANGE_LAST_VALUE)).getAttribute("value"));
    }

    public SectionPage setPublicationYearFirstValue() {
        int randomPosition = RandomNumbersUtils.getRandomNumber(0, 15);
        $(By.xpath(PUBLICATION_YEAR_RANGE_FIRST_VALUE_SLIDER)).scrollTo().waitUntil(Condition.enabled, getTimeToWait());
        dragAndDropWebElementToPosition($(By.xpath(PUBLICATION_YEAR_RANGE_FIRST_VALUE_SLIDER)), randomPosition, 0);
        pressApplyButton();
        return page(SectionPage.class);
    }

    public SectionPage setPublicationYearLastValue() {
        int randomPosition = RandomNumbersUtils.getRandomNumber(-200, -100);
        $(By.xpath(PUBLICATION_YEAR_RANGE_LAST_VALUE_SLIDER)).scrollTo().waitUntil(Condition.enabled, getTimeToWait());
        dragAndDropWebElementToPosition($(By.xpath(PUBLICATION_YEAR_RANGE_LAST_VALUE_SLIDER)), randomPosition, 0);
        pressApplyButton();
        return page(SectionPage.class);
    }

    private void pressApplyButton() {
        $(By.xpath(PUBLICATION_YEAR_FILTER_APPLY_BUTTON)).scrollTo();
        clickToSelenideElement($(By.xpath(PUBLICATION_YEAR_FILTER_APPLY_BUTTON)));
        $(By.xpath(CURRENT_FILTER_YEAR)).waitUntil(Condition.enabled, getTimeToWait());
        page(SectionPage.class);
    }

    public SectionPage setPublicationYearFilter(String yearFrom, String yearTo) {
        $(By.xpath(PUBLICATION_YEAR_RANGE_FIRST_VALUE)).scrollTo().waitUntil(Condition.enabled, getTimeToWait());
        $(By.xpath(PUBLICATION_YEAR_RANGE_FIRST_VALUE)).setValue(yearFrom).pressTab();
        $(By.xpath(PUBLICATION_YEAR_RANGE_LAST_VALUE)).setValue(yearTo).pressEnter();
        $(By.xpath(CURRENT_FILTER_YEAR)).waitUntil(Condition.enabled, getTimeToWait());
        return page(SectionPage.class);
    }

    public SectionPage clickRandomAuthor(int amountOfAuthors) {
        clickedAuthorsList = new ArrayList<>();
        for (int i = 0; i < amountOfAuthors; i++) {
            List<String> authorsList = getAuthorsList().stream().filter(item -> !clickedAuthorsList.contains(item)).collect(Collectors.toList());
            Collections.shuffle(authorsList);
            String authorToClick = authorsList.get(0);
            SelenideElement authorToClickElement = $(By.xpath(String.format(xpathAuthorToClick, authorToClick)));
            authorToClickElement.scrollTo();
            clickToSelenideElement(authorToClickElement);
            clickedAuthorsList.add(authorToClick);
            waitUntilSearchIsReady();
            $(By.xpath(CURRENT_FILTER)).shouldHave(Condition.enabled);
            $(By.xpath(CURRENT_FILTER)).shouldHave(Condition.text(authorToClick));

        }
        return page(SectionPage.class);
    }

    private List<String> getAuthorsList() {
        return $$(By.xpath(AUTHORS_LIST)).stream().map(item -> item.getAttribute("data-list-found-name")).collect(Collectors.toList());
    }

}
