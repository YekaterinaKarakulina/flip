package testClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AuthorFilter extends BaseTest {
    String xpathActualBookAuthor = "//table[@id='prod']//*[contains(@href,'people')]";
    String xpathToFindBookName = "//table[@id='prod']//span[@itemprop='name']";

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with one author")
    public void oneAuthorFilter() throws InterruptedException {
        moveToBooksPage();
        List<String> selectedAuthorNames = clickRandomAuthors(getAuthorsList(), 1);
        moveToRandomPage();
        clickOnRandomBookCard();
        String actualBookAuthor = getDriver().findElement(By.xpath(xpathActualBookAuthor)).getText();
        String bookName = getDriver().findElement(By.xpath(xpathToFindBookName)).getText();
        Assert.assertTrue(selectedAuthorNames.contains(actualBookAuthor), String.format("Actual author of book `%s` - %s. Expected author %s", bookName, actualBookAuthor, selectedAuthorNames.toString()));
    }

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with several author")
    public void severalAuthorsFilter() throws InterruptedException {
        moveToBooksPage();
        List<String> selectedAuthorNames = clickRandomAuthors(getAuthorsList(), 3);
        moveToRandomPage();
        clickOnRandomBookCard();
        String actualBookAuthor = getDriver().findElement(By.xpath(xpathActualBookAuthor)).getText();
        String bookName = getDriver().findElement(By.xpath(xpathToFindBookName)).getText();
        Assert.assertTrue(selectedAuthorNames.contains(actualBookAuthor), String.format("Actual author of book `%s` - %s. Expected author %s", bookName, actualBookAuthor, selectedAuthorNames.toString()));
    }

    private void moveToBooksPage() {
        findByXpathAndClick(getDriver(), "//a[contains(@href,'1') and contains(text(), 'Книги')]");
        findByXpathAndClick(getDriver(), "//a[@data-filter-field-sections-id='44']");
    }

    private List<String> getAuthorsList() {
        List<String> authorsList = new ArrayList<>();
        String authorName = null;
        List<WebElement> authorElements = getDriver().findElements(By.xpath("//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-default,'true')]"));
        for (int i = 0; i < authorElements.size(); i++) {
            authorName = authorElements.get(i).getAttribute("data-list-found-name");
            authorsList.add(authorName);
        }
        return authorsList;
    }

    private List<String> clickRandomAuthors(List<String> list, int amountOfAuthors) {
        List<String> clickedAuthorsList = new ArrayList<>();
        for (int i = 0; i < amountOfAuthors; i++) {
            List<String> authorsList = list.stream().filter(item -> !clickedAuthorsList.contains(item)).collect(Collectors.toList());
            Collections.shuffle(authorsList);
            String authorToClick = authorsList.get(0);
            System.out.println("author to click " + authorToClick);
            findByXpathAndClick(getDriver(), "//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-name,'" + authorToClick + "')]//*[contains(@class,'checkbox')]");
            clickedAuthorsList.add(authorsList.get(0));
            waitUntilSearchIsReady();
        }
        return clickedAuthorsList;
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
        waitUntilSearchIsReady();
        bookItem.click();
    }
}


