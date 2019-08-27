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
    String xpathActualBookAuthor = "//table[@id='prod']//*[contains(@href,'people')]";

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with one author")
    public void oneAuthorFilter() throws InterruptedException {
        moveToBooksPage();
        WebElement randomAuthorName = getRandomAuthorName();
        String expectedBookAuthor = randomAuthorName.getAttribute("data-list-found-name");
        selectRandomAuthorName(randomAuthorName);
        moveToRandomPage();
        clickOnRandomBookCard();
        String actualBookAuthor = getDriver().findElement(By.xpath(xpathActualBookAuthor)).getText();
        String bookName = getDriver().findElement(By.xpath("//table[@id='prod']//span[@itemprop='name']")).getText();
        Assert.assertEquals(actualBookAuthor, expectedBookAuthor, String.format("Actual author of book `%s` - %s. Expected author %s", bookName, actualBookAuthor, expectedBookAuthor));
    }

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with several author")
    public void severalAuthorsFilter() throws InterruptedException {
        List<String> expectedAuthorsList = new ArrayList<>();
        String authorName;
        moveToBooksPage();
    }
    
    private void moveToBooksPage() {
        findByXpathAndClick(getDriver(), "//a[contains(@href,'1') and contains(text(), 'Книги')]");
        findByXpathAndClick(getDriver(), "//a[@data-filter-field-sections-id='44']");
    }

    private WebElement getRandomAuthorName() {
        List<WebElement> authorsList = getDriver().findElements(By.xpath("//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-default,'true')]"));
        int randomName = new Random().nextInt(authorsList.size());
        WebElement randomAuthorName = authorsList.get(randomName);
        return randomAuthorName;
    }

    private void selectRandomAuthorName(WebElement element) {
        waitPageForLoad();
        element.click();
        waitUntilClickable(getDriver().findElement(By.xpath("//div[@class='f-d-select']")));
    }

    private void moveToRandomPage() throws InterruptedException {
        int actualNumber = getDriver().findElements(By.xpath("//a[@data-page]")).size();
        int randomNumber = new Random().ints(0, actualNumber + 1).findFirst().getAsInt();
        if (randomNumber != 0) {
            String xpathRandomPage = "//a[@data-page][" + randomNumber + "]";
            waitUntilSearchIsReady();
            scrollToTheEndOfPage();
            Thread.sleep(1000);
            waitUntilClickable(getDriver().findElement(By.xpath(xpathRandomPage)));
            scrollToTheEndOfPage();
            Thread.sleep(1000);
            getDriver().findElement(By.xpath(xpathRandomPage)).click();
            WebDriverWait wait = new WebDriverWait(getDriver(), 60);
            //  wait.until(element -> element.findElement(By.xpath("//div[@id='content']//*[contains(@src,'.jpg')]")).isEnabled());
            //   wait.until(element -> getDriver().findElement(By.xpath("//td[contains(@class,'pages')]//span")).getText().equals(Integer.toString(randomNumber)));
            Thread.sleep(1000);
        }
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


