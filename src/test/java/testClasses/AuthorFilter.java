package testClasses;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;


public class AuthorFilter extends BaseTest {
    Random random = new Random();


    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with one author")
    public void oneAuthorFilter() {
        String expectedName = getRandomSelectedAuthorsName();

        getToRandomPage();

        clickOnRandomBookCard();

        String bookAuthor = getDriver().findElement(By.xpath("//p[contains(@style,'margin')]/descendant-or-self::*[contains(@href,'people')]")).getText();
        Assert.assertTrue(bookAuthor.equals(expectedName));
    }

    private String getRandomSelectedAuthorsName() {
        findByXpathAndClick(getDriver(), "//a[contains(@href,'1') and contains(text(), 'Книги')]");
        findByXpathAndClick(getDriver(), "//a[@data-filter-field-sections-id='44']");
        java.util.List<WebElement> authorsList = getDriver().findElements(By.xpath("//div[@data-filter-field-list-type='peoples']/descendant-or-self::*[contains(@data-list-found-default,'true')]"));
        WebElement element = authorsList.get(random.nextInt(authorsList.size()));
        String expectedName = element.getAttribute("data-list-found-name");
        element.click();
        waitUntilClickable(getDriver().findElement(By.xpath("//div[@class='f-d-select']")));
        return expectedName;
    }

    private void getToRandomPage(){
        int actualNumber = getDriver().findElements(By.xpath("//a[@data-page]")).size();
        int randomNumber = new Random().ints(1, actualNumber + 2).findFirst().getAsInt();
        String xpath = "//a[@data-page]["+ randomNumber +"]";
        waitUntilSearchIsReady();
        scrollToTheEndOfPage();
        waitUntilClickable(getDriver().findElement(By.xpath(xpath)));
        scrollToTheEndOfPage();
        getDriver().findElement(By.xpath(xpath)).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        wait.until(element -> element.findElement(By.xpath("//div[@id='content']/descendant-or-self::*[contains(@src,'.jpg')]")).isEnabled());
    }

    private void clickOnRandomBookCard(){
        List<WebElement> booksList = getDriver().findElements(By.xpath("//div[@class='placeholder']//a[@class='title']"));

        System.out.println(booksList.size());
        WebElement bookItem = booksList.get(random.nextInt(booksList.size()));
        waitUntilClickable(bookItem);
        scrollToElement(bookItem);
        bookItem.click();
    }

}

