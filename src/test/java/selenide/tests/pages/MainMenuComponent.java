package selenide.tests.pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class MainMenuComponent extends BasePage {
    private static final String menu = "//p[contains(text(),'Каталог')]/ancestor::div[contains(@class,'menu')]";
    private static final String booksSection = "//ul[contains(@class,'sub-1')]/li/a[contains(text(), 'Книги')]";
    private static final String imaginativeLiteratureSection = "//a[@data-filter-field-sections-id='44']";
    private static final String filter = "//p[text()='Фильтр']";

    public MainMenuComponent clickBookSection() {
        $(By.xpath(menu)).hover();
        clickToSelenideElement($(By.xpath(booksSection)));
        $(By.xpath(filter)).waitUntil(Condition.enabled, 1000);
        return page(MainMenuComponent.class);
    }

    public SearchCriteria clickImaginativeLiteratureSection() {
        clickToSelenideElement($(By.xpath(imaginativeLiteratureSection)));
        $(By.xpath(imaginativeLiteratureSection)).waitUntil(Condition.attribute("class", "active"), 1000);
        return page(SearchCriteria.class);
    }

}
