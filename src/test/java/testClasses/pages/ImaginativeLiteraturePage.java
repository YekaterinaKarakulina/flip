package testClasses.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ImaginativeLiteraturePage extends AbstractPage {
    private static final By AUTHORS_LIST_LOCATOR = By.xpath("//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-default,'true')]");
    private static final By PUBLICATION_YEAR_RANGE_FIRST_VALUE_LOCATOR = By.xpath("//div[@class='filter-left']//input[@placeholder='2000']");
    private static final By PUBLICATION_YEAR_RANGE_LAST_VALUE_LOCATOR = By.xpath("//div[@class='filter-left']//input[@placeholder='2098']");
    private static final By PUBLICATION_YEAR_FILTER_APPLY_BUTTON = By.xpath("//li[@data-filter-range-max='2098']//button[text()='Применить']");
    private static String xpathAuthorToClick = "//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-name,'%s')]//*[contains(@class,'checkbox')]";
    private List<String> clickedAuthorsList;

    public List<String> getClickedAuthorsList() {
        return clickedAuthorsList;
    }

    ImaginativeLiteraturePage(WebDriver driver) {
        super(driver);
    }

    private List<String> getAuthorsList() {
        List<WebElement> authorsElements = driver.findElements(AUTHORS_LIST_LOCATOR);
        return authorsElements.stream().map(item -> item.getAttribute("data-list-found-name")).collect(Collectors.toList());
    }

    public ImaginativeLiteraturePage setPublicationYearFirstValue(String year) {
        driver.findElement(PUBLICATION_YEAR_RANGE_FIRST_VALUE_LOCATOR).sendKeys(year);
        return this;
    }

    public ImaginativeLiteraturePage setPublicationYearLastValue(String year) {
        driver.findElement(PUBLICATION_YEAR_RANGE_LAST_VALUE_LOCATOR).sendKeys(year);
        return this;
    }

    public SearchResultsPage applyPublicationYearFilter() {
        driver.findElement(PUBLICATION_YEAR_FILTER_APPLY_BUTTON).click();
        return new SearchResultsPage(driver);
    }

    public SearchResultsPage clickRandomAuthor(int amountOfAuthors) {
        clickedAuthorsList = new ArrayList<>();
        for (int i = 0; i < amountOfAuthors; i++) {
            List<String> authorsList = getAuthorsList().stream().filter(item -> !clickedAuthorsList.contains(item)).collect(Collectors.toList());
            Collections.shuffle(authorsList);
            String authorToClick = authorsList.get(0);
            System.out.println("author to click " + authorToClick);
            driver.findElement(By.xpath(String.format(xpathAuthorToClick, authorToClick))).click();
            clickedAuthorsList.add(authorsList.get(0));
            waitUntilSearchIsReady();
            waitUntilElementHasText(By.xpath("//div[contains(@class,'filters')]"), authorToClick);
        }
        return new SearchResultsPage(driver);
    }
}
