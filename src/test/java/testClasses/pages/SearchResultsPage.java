package testClasses.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Random;

public class SearchResultsPage extends AbstractPage {
    String xpathRandomPage = "//a[@data-page='%s']";
    private static final By AMOUNT_OF_PAGES_LOCATOR = By.xpath("//a[@data-page]");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public RandomSearchResultsPage moveToRandomPage() {
        waitUntilSearchIsReady();
        int actualNumber = driver.findElements(AMOUNT_OF_PAGES_LOCATOR).size();
        int randomNumber = new Random().ints(1, actualNumber + 1).findFirst().getAsInt();
        if (randomNumber != 1) {
            waitUntilSearchIsReady();
            scrollToTheEndOfPage();
            waitUntilClickable(driver.findElement(By.xpath(String.format(xpathRandomPage, randomNumber))));
            scrollToTheEndOfPage();
            driver.findElement(By.xpath(String.format(xpathRandomPage, randomNumber))).click();
            scrollToTheEndOfPage();
            waitUntilSearchIsReady();
            waitUntilElementHasText(By.xpath("//td[contains(@class,'pages')]//span"), Integer.toString(randomNumber));
        }
        return new RandomSearchResultsPage(driver);
    }


}
