package testClasses;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;



public class AuthorFilter extends BaseTest {
    Random random = new Random();


    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with one author")
    public void oneAuthorFilter() {
        String expectedName = getRandomSelectedAuthorsName();

        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='f-d-select']")));

        java.util.List<WebElement> pages = getDriver().findElements(By.xpath("//a[@data-page]"));
        WebElement pageNumber = pages.get(random.nextInt(pages.size()));
        scrollToElement(pageNumber);
        wait.until(ExpectedConditions.elementToBeClickable(pageNumber));
        pageNumber.click();

        wait.until(element -> element.findElement(By.xpath("//div[@id='content']/descendant-or-self::*[contains(@src,'.jpg')]")).isEnabled());

        java.util.List<WebElement> booksList = getDriver().findElements(By.xpath("//div[@id='content']/descendant-or-self::*[contains(@src,'.jpg')]"));
        System.out.println(booksList.size());
        WebElement bookItem = booksList.get(random.nextInt(booksList.size()));

        wait.until(element -> bookItem.isEnabled());

        bookItem.click();
        String bookAuthor = getDriver().findElement(By.xpath("//p[contains(@style,'margin')]/descendant-or-self::*[contains(@href,'people')]")).getText();
        Assert.assertTrue(bookAuthor.equals(expectedName));
        findByXpathAndClick(getDriver(), "//div[contains(@class,'logo cell')]");
    }

    public String getRandomSelectedAuthorsName() {
        findByXpathAndClick(getDriver(), "//a[contains(@href,'1') and contains(text(), 'Книги')]");
        findByXpathAndClick(getDriver(), "//a[@data-filter-field-sections-id='44']");
        java.util.List<WebElement> authorsList = getDriver().findElements(By.xpath("//div[@data-filter-field-list-type='peoples']/descendant-or-self::*[contains(@data-list-found-default,'true')]"));
        WebElement element = authorsList.get(random.nextInt(authorsList.size()));
        String expectedName = element.getAttribute("data-list-found-name");
        element.click();
        return expectedName;
    }

}

