package selenium.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Checkbox extends BasePage {

    private static String xpathAuthorToClick = "//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-name,'%s')]//*[contains(@class,'checkbox')]";

    @FindBy(xpath = "//div[@data-filter-field-list-type='peoples']//*[contains(@class,'selected')]//*[contains(@class,'filter-label')]")
    private static List<WebElement> selectedAuthors;

    public Checkbox(WebDriver driver) {
        super(driver);
    }

    public void checkBox(String authorToClick) {
        List<String> selectedAuthors = Checkbox.selectedAuthors.stream().map(WebElement::getText).collect(Collectors.toList());
        if(!selectedAuthors.contains(authorToClick))
        {
            WebElement authorToClickElement = getDriver().findElement(By.xpath(String.format(xpathAuthorToClick, authorToClick)));
            scrollToElement(authorToClickElement);
            waitForElementEnabled(authorToClickElement);
            clickToWebElement(authorToClickElement);
        }
    }

}
