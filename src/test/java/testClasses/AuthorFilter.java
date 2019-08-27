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
        List<String> randomAuthorNames = getRandomAuthorName(1);
        String expectedBookAuthor = randomAuthorNames.get(0);
        selectRandomAuthorNames(randomAuthorNames);
        moveToRandomPage();
        clickOnRandomBookCard();
        String actualBookAuthor = getDriver().findElement(By.xpath(xpathActualBookAuthor)).getText();
        String bookName = getDriver().findElement(By.xpath("//table[@id='prod']//span[@itemprop='name']")).getText();
        Assert.assertEquals(actualBookAuthor, expectedBookAuthor, String.format("Actual author of book `%s` - %s. Expected author %s", bookName, actualBookAuthor, expectedBookAuthor));
    }

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with several author")
    public void severalAuthorsFilter() throws InterruptedException {
        moveToBooksPage();
        List<String> randomAuthorNames = getRandomAuthorName(3);
        selectRandomAuthorNames(randomAuthorNames);
        moveToRandomPage();
        clickOnRandomBookCard();
        String actualBookAuthor = getDriver().findElement(By.xpath(xpathActualBookAuthor)).getText();
        String bookName = getDriver().findElement(By.xpath("//table[@id='prod']//span[@itemprop='name']")).getText();
        Assert.assertTrue(randomAuthorNames.contains(actualBookAuthor),  String.format("Actual author of book `%s` - %s. Expected author %s", bookName, actualBookAuthor, randomAuthorNames.toString()));
    }


    private void moveToBooksPage() {
        findByXpathAndClick(getDriver(), "//a[contains(@href,'1') and contains(text(), 'Книги')]");
        findByXpathAndClick(getDriver(), "//a[@data-filter-field-sections-id='44']");
    }

    private List<String> getRandomAuthorName(int amountOfAuthors) {
        List<String> authorsList = new ArrayList<>();
        String authorName = null;
        int randomName = 0;
        List<WebElement> webElementList = getDriver().findElements(By.xpath("//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-default,'true')]"));
        for (int i = 0; i < amountOfAuthors; i++) {
            randomName = new Random().nextInt(webElementList.size());
            authorName = webElementList.get(randomName).getAttribute("data-list-found-name");
            authorsList.add(authorName);
        }
        return authorsList;
    }

    private void selectRandomAuthorNames(List<String> authorNames) throws InterruptedException {
        for(int i=0; i<authorNames.size(); i++)
        {
            WebElement authorToSelect = getDriver().findElement(By.xpath("//div[@data-filter-field-list-type='peoples']//li[contains(@data-list-found-name, '" + authorNames.get(i) + "')]"));
            authorToSelect.click();
            Thread.sleep(2000);
        }
    }

    private void moveToRandomPage() throws InterruptedException {
        List<WebElement> pages = getDriver().findElements(By.xpath("//a[@data-page]"));

        String lastPageAttribute = pages.get(pages.size() - 1).getText();
        System.out.println("page attr" + lastPageAttribute);
        int actualNumber = getDriver().findElements(By.xpath("//a[@data-page]")).size();
        if (lastPageAttribute.equals(">>")) {
            System.out.println(">>>>>>>");
        } else {
            System.out.println("else");
        }

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


