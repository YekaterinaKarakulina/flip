package testClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AuthorFilter extends BaseTest {
    String bookName = null;
    String actualBookAuthor = null;
    String expectedBookAuthor = null;

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with one author")
    public void oneAuthorFilter() throws InterruptedException {
        moveToBooksPage();
        expectedBookAuthor = getRandomSelectedAuthorsName();
        moveToRandomPage();
        clickOnRandomBookCard();
        actualBookAuthor = getDriver().findElement(By.xpath("//table[@id='prod']//*[contains(@href,'people')]")).getText();
        bookName = getDriver().findElement(By.xpath("//table[@id='prod']//span[@itemprop='name']")).getText();
        Assert.assertEquals(actualBookAuthor, expectedBookAuthor, "Actual author of book \"" + bookName + "\" - " + actualBookAuthor + ". Expected author " + expectedBookAuthor);
    }

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with several author")
    public void severalAuthorsFilter() throws InterruptedException {
        List<String> expectedAuthorsList = new ArrayList<>();
        String authorName;
        moveToBooksPage();
        for (int i = 0; i < 3; i++) {
            authorName = getRandomSelectedAuthorsName();
            waitUntilSearchIsReady();
            Thread.sleep(1000);
            expectedAuthorsList.add(authorName);
        }
    }

    private void moveToBooksPage() {
        findByXpathAndClick(getDriver(), "//a[contains(@href,'1') and contains(text(), 'Книги')]");
        findByXpathAndClick(getDriver(), "//a[@data-filter-field-sections-id='44']");
    }

    private String getRandomSelectedAuthorsName() {
        List<WebElement> authorsList = getDriver().findElements(By.xpath("//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-default,'true')]"));
        int randomName = new Random().nextInt(authorsList.size());
        WebElement element = authorsList.get(randomName);
        String expectedName = element.getAttribute("data-list-found-name");
        waitPageForLoad();
        element.click();
        waitUntilClickable(getDriver().findElement(By.xpath("//div[@class='f-d-select']")));
        return expectedName;
    }

    private void moveToRandomPage() throws InterruptedException {
        int actualNumber = getDriver().findElements(By.xpath("//a[@data-page]")).size();
        int randomNumber = new Random().ints(0, actualNumber + 1).findFirst().getAsInt();
        if (randomNumber != 0) {
            String xpath = "//a[@data-page][" + randomNumber + "]";
            waitUntilSearchIsReady();
            scrollToTheEndOfPage();
            Thread.sleep(1000);
            waitUntilClickable(getDriver().findElement(By.xpath(xpath)));
            scrollToTheEndOfPage();
            Thread.sleep(1000);
            getDriver().findElement(By.xpath(xpath)).click();
        }
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        wait.until(element -> element.findElement(By.xpath("//div[@id='content']//*[contains(@src,'.jpg')]")).isEnabled());
        Thread.sleep(1000);
    }

    private void clickOnRandomBookCard() throws InterruptedException {
        List<WebElement> booksList = getDriver().findElements(By.xpath("//div[@class='placeholder']//a[@class='title']"));
        int randomBook = new Random().nextInt(booksList.size());
        WebElement bookItem = booksList.get(randomBook);
        waitUntilClickable(bookItem);
        scrollToElement(bookItem);
        Thread.sleep(1000);
        bookItem.click();
    }
}


