package selenide.tests.pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class MainMenuComponent extends BasePage {

    private static final By MENU = By.xpath("//p[contains(text(),'Каталог')]/ancestor::div[contains(@class,'menu')]");
    private static final By BOOKS_SECTION = By.xpath("//ul[contains(@class,'sub-1')]/li/a[contains(text(), 'Книги')]");
    private static final By IMAGINATIVE_LITERATURE_SECTION = By.xpath("//a[@data-filter-field-sections-id='44']");
    private static final By FILTER = By.xpath("//p[text()='Фильтр']");

    public MainMenuComponent clickBookSection() {
        $(MENU).hover();
        clickToSelenideElement($(BOOKS_SECTION));
        $(FILTER).waitUntil(Condition.enabled, getTimeToWait());
        return page(MainMenuComponent.class);
    }

    public SearchCriteria clickImaginativeLiteratureSection() {
        clickToSelenideElement($(IMAGINATIVE_LITERATURE_SECTION));
        $(IMAGINATIVE_LITERATURE_SECTION).waitUntil(Condition.attribute("class", "active"), getTimeToWait());
        return page(SearchCriteria.class);
    }

}
