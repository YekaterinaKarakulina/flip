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
    private static String xpathAuthorToClick = "//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-name,'%s')]//*[contains(@class,'checkbox')]";

    public ImaginativeLiteraturePage(WebDriver driver) {
        super(driver);
    }

    public List<String> getAuthorsList() {
        List<WebElement> authorsElements = driver.findElements(AUTHORS_LIST_LOCATOR);
        return authorsElements.stream().map(item -> item.getAttribute("data-list-found-name")).collect(Collectors.toList());
    }

    public String chooseRandomAuthor(List<String> list)
    {
        List<String> authorsList = new ArrayList<>(list);
        Collections.shuffle(authorsList);
        return authorsList.get(0);
    }

    public SearchResultsPage clickRandomAuthor(String authorToClick) {
        driver.findElement(By.xpath(String.format(xpathAuthorToClick, authorToClick))).click();
        waitUntilSearchIsReady();
        waitUntilElementHasText(By.xpath("//div[contains(@class,'filters')]"), authorToClick);
        return new SearchResultsPage(driver);
    }




}
