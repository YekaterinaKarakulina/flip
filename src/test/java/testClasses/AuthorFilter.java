package testClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AuthorFilter extends BaseTest {
    String xpathActualBookAuthor = "//table[@id='prod']//*[contains(@href,'people')]";
    String xpathToFindBookName = "//table[@id='prod']//span[@itemprop='name']";
    String xPathPublicationYear = "//div[@class='description-table']//div[a[@href]]";
    String xPathPublicationYearRangeFirstValue = "//div[@class='filter-left']//input[@placeholder='2000']";
    String xPathPublicationYearRangeLastValue = "//div[@class='filter-left']//input[@placeholder='2098']";
    String xPathForApplyPublicationYearFilter = "//li[@data-filter-range-max='2098']//button[text()='Применить']";

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with one author")
    public void oneAuthorFilter() {
        moveToBooksPage();
        List<String> selectedAuthorNames = clickRandomAuthors(1);
        moveToRandomPage();
        clickOnRandomBookCard();
        String actualBookAuthor = getDriver().findElement(By.xpath(xpathActualBookAuthor)).getText();
        String bookName = getDriver().findElement(By.xpath(xpathToFindBookName)).getText();
        Assert.assertTrue(selectedAuthorNames.contains(actualBookAuthor), String.format("Actual author of book `%s` - %s. Expected author %s", bookName, actualBookAuthor, selectedAuthorNames.toString()));
    }

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with several author")
    public void severalAuthorsFilter() {
        moveToBooksPage();
        List<String> selectedAuthorNames = clickRandomAuthors(3);
        moveToRandomPage();
        clickOnRandomBookCard();
        String actualBookAuthor = getDriver().findElement(By.xpath(xpathActualBookAuthor)).getText();
        String bookName = getDriver().findElement(By.xpath(xpathToFindBookName)).getText();
        Assert.assertTrue(selectedAuthorNames.contains(actualBookAuthor), String.format("Actual author of book `%s` - %s. Expected author %s", bookName, actualBookAuthor, selectedAuthorNames.toString()));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with only range first value")
    public void publicationYearFilterRangeFirstValue() {
        moveToBooksPage();
        int publicationYearRangeFirstValue = new Random().ints(2000, 2019 + 1).findFirst().getAsInt();
        setPublicationYearFirstValue(Integer.toString(publicationYearRangeFirstValue));
        moveToRandomPage();
        clickOnRandomBookCard();
        int actualPublicationYear = getPublicationYearInfoByXpath(xPathPublicationYear);
        String bookName = getDriver().findElement(By.xpath(xpathToFindBookName)).getText();
        Assert.assertTrue(actualPublicationYear >= publicationYearRangeFirstValue, String.format("Actual publication year of book + `%s` - %s. Expected year of publication %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeFirstValue)));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with only range last value")
    public void publicationYearFilterRangeLastValue() {
        moveToBooksPage();
        int publicationYearRangeLastValue = new Random().ints(2000, 2098 + 1).findFirst().getAsInt();
        setPublicationYearLastValue(Integer.toString(publicationYearRangeLastValue));
        moveToRandomPage();
        clickOnRandomBookCard();
        int actualPublicationYear = getPublicationYearInfoByXpath(xPathPublicationYear);
        String bookName = getDriver().findElement(By.xpath(xpathToFindBookName)).getText();
        Assert.assertTrue(actualPublicationYear <= publicationYearRangeLastValue, String.format("Actual publication year of book + `%s` - %s. Expected year of publication %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeLastValue)));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with full range")
    public void publicationYearFilterFullRange() {
        moveToBooksPage();
        int publicationYearRangeFirstValue = new Random().ints(2000, 2010 + 1).findFirst().getAsInt();
        int publicationYearRangeLastValue = new Random().ints(2010, 2098 + 1).findFirst().getAsInt();
        setPublicationYearFirstValue(Integer.toString(publicationYearRangeFirstValue));
        setPublicationYearLastValue(Integer.toString(publicationYearRangeLastValue));
        moveToRandomPage();
        clickOnRandomBookCard();
        int actualPublicationYear = getPublicationYearInfoByXpath(xPathPublicationYear);
        String bookName = getDriver().findElement(By.xpath(xpathToFindBookName)).getText();
        Assert.assertTrue(publicationYearRangeFirstValue <= actualPublicationYear && actualPublicationYear <= publicationYearRangeLastValue, String.format("Actual publication year of book + `%s` - %s. Expected range of publication %s - %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeFirstValue), Integer.toString(publicationYearRangeLastValue)));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with full range")
    public void AuthorNameAndPublicationYearFilters() {
        moveToBooksPage();
        List<String> selectedAuthorNames = clickRandomAuthors(1);
        int publicationYearRangeFirstValue = new Random().ints(2000, 2010 + 1).findFirst().getAsInt();
        int publicationYearRangeLastValue = new Random().ints(2010, 2098 + 1).findFirst().getAsInt();
        setPublicationYearFirstValue(Integer.toString(publicationYearRangeFirstValue));
        setPublicationYearLastValue(Integer.toString(publicationYearRangeLastValue));
        waitUntilSearchIsReady();
        moveToRandomPage();
        clickOnRandomBookCard();
        int actualPublicationYear = getPublicationYearInfoByXpath(xPathPublicationYear);
        String bookName = getDriver().findElement(By.xpath(xpathToFindBookName)).getText();
        String actualBookAuthor = getDriver().findElement(By.xpath(xpathActualBookAuthor)).getText();
        SoftAssert twoFilters = new SoftAssert();
        twoFilters.assertTrue(publicationYearRangeFirstValue <= actualPublicationYear && actualPublicationYear <= publicationYearRangeLastValue, String.format("Actual publication year of book + `%s` - %s. Expected range of publication %s - %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeFirstValue), Integer.toString(publicationYearRangeLastValue)));
        twoFilters.assertTrue(selectedAuthorNames.contains(actualBookAuthor), String.format("Actual author of book `%s` - %s. Expected author %s", bookName, actualBookAuthor, selectedAuthorNames.toString()));
        twoFilters.assertAll();
    }

    private void moveToBooksPage() {
        clickToElementByXpath("//a[contains(@href,'1') and contains(text(), 'Книги')]");
        clickToElementByXpath("//a[@data-filter-field-sections-id='44']");
    }

    private List<String> getAuthorsList() {
        List<WebElement> authorElements = getDriver().findElements(By.xpath("//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-default,'true')]"));
        return authorElements.stream().map(item -> item.getAttribute("data-list-found-name")).collect(Collectors.toList());
    }

    private List<String> clickRandomAuthors(int amountOfAuthors) {
        List<String> clickedAuthorsList = new ArrayList<>();
        for (int i = 0; i < amountOfAuthors; i++) {
            List<String> authorsList = getAuthorsList().stream().filter(item -> !clickedAuthorsList.contains(item)).collect(Collectors.toList());
            Collections.shuffle(authorsList);
            String authorToClick = authorsList.get(0);
            System.out.println("author to click " + authorToClick);
            clickToElementByXpath("//div[@data-filter-field-list-type='peoples']//*[contains(@data-list-found-name,'" + authorToClick + "')]//*[contains(@class,'checkbox')]");
            clickedAuthorsList.add(authorsList.get(0));
            waitUntilSearchIsReady();
            waitUntilElementHasText(By.xpath("//div[contains(@class,'filters')]"), authorToClick);
        }
        return clickedAuthorsList;
    }

    private void moveToRandomPage() {
        waitUntilSearchIsReady();
        int actualNumber = getDriver().findElements(By.xpath("//a[@data-page]")).size();
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
            waitUntilElementHasText(By.xpath("//td[contains(@class,'pages')]//span"), Integer.toString(randomNumber));
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

    private int getPublicationYearInfoByXpath(String xpath) {
        WebElement addInfo = getDriver().findElement(By.xpath(xpath));
        String expectedPublicationYearRangeMinString = addInfo.getText().replaceAll("\\D+", "");
        return Integer.parseInt(expectedPublicationYearRangeMinString);
    }

    private void setPublicationYearFirstValue(String year) {
        typeToElementByXpath(xPathPublicationYearRangeFirstValue, year);
        clickToElementByXpath(xPathForApplyPublicationYearFilter);
    }

    private void setPublicationYearLastValue(String year) {
        typeToElementByXpath(xPathPublicationYearRangeLastValue, year);
        clickToElementByXpath(xPathForApplyPublicationYearFilter);
    }
}


