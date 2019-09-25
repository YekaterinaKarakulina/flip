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

    private static final By CURRENT_FILTER = By.xpath("//div[contains(@class,'filters')]");
    private static final By CURRENT_FILTER_YEAR = By.xpath("//div[contains(@class,'filters')]//*[contains(@title, 'Год')]/span");
    private static final By PUBLICATION_YEAR_RANGE_FIRST_VALUE = By.xpath("//p[contains(text(),'Год издания')]/following::*[contains(text(), 'от')]//input");
    private static final By PUBLICATION_YEAR_RANGE_LAST_VALUE = By.xpath("//p[contains(text(),'Год издания')]/following::*[contains(text(), 'до')]//input");
    private static final By PUBLICATION_YEAR_RANGE_FIRST_VALUE_SLIDER = By.xpath("//p[contains(text(),'Год издания')]/following::a[contains(@class,'slider')][1]");
    private static final By PUBLICATION_YEAR_RANGE_LAST_VALUE_SLIDER = By.xpath("//p[contains(text(),'Год издания')]/following::a[contains(@class,'slider')][2]");
    private static final By PUBLICATION_YEAR_FILTER_APPLY_BUTTON = By.xpath("//p[contains(text(),'Год издания')]/following::*[contains(text(), 'Применить')]");
    private static final By AUTHORS_LIST = By.xpath("//div[@data-filter-field-list-type='peoples']//li[@data-list-found-name]");

    private static String xpathAuthorToClick = "//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-name,'%s')]//*[contains(@class,'checkbox')]";
    private List<String> clickedAuthorsList;

    public List<String> getSelectedAuthorsList() {
        return clickedAuthorsList;
    }


    public int getExpectedPublicationYearFirstValue() {
        $(PUBLICATION_YEAR_RANGE_FIRST_VALUE).waitUntil(Condition.enabled, getTimeToWait());
        return Integer.parseInt($(PUBLICATION_YEAR_RANGE_FIRST_VALUE).attr("value"));
    }

    public int getExpectedPublicationYearLastValue() {
        $(PUBLICATION_YEAR_RANGE_LAST_VALUE).waitUntil(Condition.enabled, getTimeToWait());
        return Integer.parseInt($(PUBLICATION_YEAR_RANGE_LAST_VALUE).attr("value"));
    }

    public SectionPage setPublicationYearFirstValue() {
        int randomPosition = RandomNumbersUtils.getRandomNumber(0, 15);
        $(PUBLICATION_YEAR_RANGE_FIRST_VALUE_SLIDER).scrollTo().waitUntil(Condition.enabled, getTimeToWait());
        dragAndDropWebElementToPosition($(PUBLICATION_YEAR_RANGE_FIRST_VALUE_SLIDER), randomPosition, 0);
        pressApplyButton();
        return page(SectionPage.class);
    }

    public SectionPage setPublicationYearLastValue() {
        int randomPosition = RandomNumbersUtils.getRandomNumber(-200, -100);
        $(PUBLICATION_YEAR_RANGE_LAST_VALUE_SLIDER).scrollTo().waitUntil(Condition.enabled, getTimeToWait());
        dragAndDropWebElementToPosition($(PUBLICATION_YEAR_RANGE_LAST_VALUE_SLIDER), randomPosition, 0);
        pressApplyButton();
        return page(SectionPage.class);
    }

    private void pressApplyButton() {
        $(PUBLICATION_YEAR_FILTER_APPLY_BUTTON).scrollTo();
        clickToSelenideElement($(PUBLICATION_YEAR_FILTER_APPLY_BUTTON));
        $(CURRENT_FILTER_YEAR).waitUntil(Condition.enabled, getTimeToWait());
        page(SectionPage.class);
    }

    public SectionPage setPublicationYearFilter(String yearFrom, String yearTo) {
        $(PUBLICATION_YEAR_RANGE_FIRST_VALUE).scrollTo().waitUntil(Condition.enabled, getTimeToWait());
        $(PUBLICATION_YEAR_RANGE_FIRST_VALUE).setValue(yearFrom).pressTab();
        $(PUBLICATION_YEAR_RANGE_LAST_VALUE).setValue(yearTo).pressEnter();
        $(CURRENT_FILTER_YEAR).waitUntil(Condition.enabled, getTimeToWait());
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
            $(CURRENT_FILTER).shouldHave(Condition.enabled);
            $(CURRENT_FILTER).shouldHave(Condition.text(authorToClick));
        }
        return page(SectionPage.class);
    }

    private List<String> getAuthorsList() {
        return $$(AUTHORS_LIST).stream().map(item -> item.attr("data-list-found-name")).collect(Collectors.toList());
    }

}
