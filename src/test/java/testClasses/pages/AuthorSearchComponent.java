package testClasses.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorSearchComponent extends AbstractPage {

    private static final By AUTHORS_LIST_LOCATOR = By.xpath("//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-default,'true')]");
    private static String xpathAuthorToClick = "//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-name,'%s')]//*[contains(@class,'checkbox')]";
    private List<String> clickedAuthorsList;

    public List<String> getClickedAuthorsList() {
        List<WebElement> list = getDriver().findElements(By.xpath("//div[contains(@id,'filter-header')]//span"));
        return list.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    private List<String> getAuthorsList() {
        List<WebElement> authorsElements = getDriver().findElements(AUTHORS_LIST_LOCATOR);
        return authorsElements.stream().map(item -> item.getAttribute("data-list-found-name")).collect(Collectors.toList());
    }

    public SectionPage clickRandomAuthor(int amountOfAuthors) {
        clickedAuthorsList = new ArrayList<>();
        for (int i = 0; i < amountOfAuthors; i++) {
            List<String> authorsList = getAuthorsList().stream().filter(item -> !clickedAuthorsList.contains(item)).collect(Collectors.toList());
            Collections.shuffle(authorsList);
            String authorToClick = authorsList.get(0);
            System.out.println("author to click " + authorToClick);
            getDriver().findElement(By.xpath(String.format(xpathAuthorToClick, authorToClick))).click();
            clickedAuthorsList.add(authorsList.get(0));
            for (int k = 0; k < clickedAuthorsList.size(); k++) {
                System.out.println("author " + clickedAuthorsList.get(k));
            }
            waitUntilSearchIsReady();
            waitUntilElementHasText(By.xpath("//div[contains(@class,'filters')]"), authorToClick);
        }
        return new SectionPage(getDriver());
    }

    AuthorSearchComponent(WebDriver driver) {
        super(driver);
    }
}
