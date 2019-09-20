package selenide.tests.pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class MainMenuComponent extends BasePage {

    private static final String MENU = "//p[contains(text(),'Каталог')]/ancestor::div[contains(@class,'menu')]";
    private static final String BOOKS_SECTION = "//ul[contains(@class,'sub-1')]/li/a[contains(text(), 'Книги')]";
    private static final String IMAGINATIVE_LITERATURE_SECTION = "//a[@data-filter-field-sections-id='44']";
    private static final String FILTER = "//p[text()='Фильтр']";

    public MainMenuComponent clickBookSection() {
        $(By.xpath(MENU)).hover();
        clickToSelenideElement($(By.xpath(BOOKS_SECTION)));
        $(By.xpath(FILTER)).waitUntil(Condition.enabled, getTimeToWait());
        return page(MainMenuComponent.class);
    }

    public SearchCriteria clickImaginativeLiteratureSection() {
        clickToSelenideElement($(By.xpath(IMAGINATIVE_LITERATURE_SECTION)));
        $(By.xpath(IMAGINATIVE_LITERATURE_SECTION)).waitUntil(Condition.attribute("class", "active"), getTimeToWait());
        return page(SearchCriteria.class);
    }

}
