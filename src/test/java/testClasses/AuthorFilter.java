package testClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AuthorFilter extends BaseTest {
    String xpathActualBookAuthor = "//table[@id='prod']//*[contains(@href,'people')]";
    String xpathToFindBookName = "//table[@id='prod']//span[@itemprop='name']";

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with one author")
    public void oneAuthorFilter() throws InterruptedException {
        moveToBooksPage();
        List<String> randomAuthorNames = getRandomAuthorName(1);
        String expectedBookAuthor = randomAuthorNames.get(0);
        selectRandomAuthorNames(randomAuthorNames);
        moveToRandomPage();
        clickOnRandomBookCard();
        String actualBookAuthor = getDriver().findElement(By.xpath(xpathActualBookAuthor)).getText();
        String bookName = getDriver().findElement(By.xpath(xpathToFindBookName)).getText();
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
        String bookName = getDriver().findElement(By.xpath(xpathToFindBookName)).getText();
        Assert.assertTrue(randomAuthorNames.contains(actualBookAuthor), String.format("Actual author of book `%s` - %s. Expected author %s", bookName, actualBookAuthor, randomAuthorNames.toString()));
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
            webElementList.remove(randomName);
        }
        return authorsList;
    }

    private void selectRandomAuthorNames(List<String> authorNames) {
        for (int i = 0; i < authorNames.size(); i++) {
            findByXpathAndType(getDriver(), "//div[@data-filter-field-list-type='peoples']//*[@placeholder='Введите название']", authorNames.get(i));
            findByXpathAndClick(getDriver(), "//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-name,'" + authorNames.get(i) + "')]");
            waitUntilSearchIsReady();
        }
    }

    private void moveToRandomPage() throws InterruptedException {
        List<WebElement> pages = getDriver().findElements(By.xpath("//a[@data-page]"));
        String lastPageAttribute = pages.get(pages.size() - 1).getText();
        int actualNumber = getDriver().findElements(By.xpath("//a[@data-page]")).size();
        if (lastPageAttribute.equals("»")) {
            findByXpathAndClick(getDriver(), "//a[@data-page][contains(text()," + actualNumber + ")]");
            scrollToTheEndOfPage();
            waitUntilSearchIsReady();
            moveToRandomPage();
        } else {
            int randomNumber = new Random().ints(1, actualNumber + 1).findFirst().getAsInt();
            if (randomNumber != 1) {
                String xpathRandomPage = "//a[@data-page='" + randomNumber + "']";
                waitUntilSearchIsReady();
                scrollToTheEndOfPage();
                waitUntilClickable(getDriver().findElement(By.xpath(xpathRandomPage)));
                scrollToTheEndOfPage();
                getDriver().findElement(By.xpath(xpathRandomPage)).click();
                scrollToTheEndOfPage();
                waitUntilSearchIsReady();
                Thread.sleep(1000);
                waitUntilElementHasText(By.xpath("//td[contains(@class,'pages')]//span"), Integer.toString(randomNumber));
            }
        }
    }

    private void clickOnRandomBookCard() {
        List<WebElement> booksList = getDriver().findElements(By.xpath("//div[@class='placeholder']//a[@class='title']"));
        int randomBook = new Random().nextInt(booksList.size());
        WebElement bookItem = booksList.get(randomBook);
        waitUntilClickable(bookItem);
        scrollToElement(bookItem);
        waitUntilClickable(bookItem);
        bookItem.click();
    }
}


