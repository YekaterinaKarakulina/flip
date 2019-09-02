package testClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testClasses.pages.HomePage;
import testClasses.pages.ImaginativeLiteraturePage;
import testClasses.pages.RandomBookPage;
import testClasses.pages.RandomSearchResultsPage;
import testClasses.pages.SearchResultsPage;

import java.util.List;
import java.util.Random;

public class FlipTest {
    private WebDriver driver;
    private static final By MAIN_PAGE_LOCATOR = By.xpath("//div[contains(@class,'logo cell')]");

    @BeforeSuite
    public void initBrowser() {
        //System.setProperty("webdriver.gecko.driver", "src/test/java/resources/geckodriver"); //this for my home PC with linux
        //driver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "src/test/java/resources/chromedriver76.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void goToMainPage() {
        driver.findElement(MAIN_PAGE_LOCATOR).click();
    }

    @AfterSuite
    public void afterSuite() {
        driver.close();
    }

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with one author")
    public void oneAuthorFilter() {
        ImaginativeLiteraturePage imaginativeLiteraturePage = new HomePage(driver).open().chooseBookSection().chooseImaginativeLiteratureSection();
        SearchResultsPage searchResultsPage = imaginativeLiteraturePage.clickRandomAuthor(1);
        List<String> selectedAuthorNames = imaginativeLiteraturePage.getClickedAuthorsList();
        RandomSearchResultsPage randomSearchResultsPage = searchResultsPage.moveToRandomPage();
        RandomBookPage randomBookPage = randomSearchResultsPage.clickOnRandomBookCard();
        String actualBookAuthor = randomBookPage.getBookAuthor();
        String bookName = new RandomBookPage(driver).getBookName();
        Assert.assertTrue(selectedAuthorNames.contains(actualBookAuthor), String.format("Actual author of book `%s` - %s. Expected author %s", bookName, actualBookAuthor, selectedAuthorNames.toString()));
    }

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with several author")
    public void severalAuthorsFilter() {
        ImaginativeLiteraturePage imaginativeLiteraturePage = new HomePage(driver).open().chooseBookSection().chooseImaginativeLiteratureSection();
        SearchResultsPage searchResultsPage = imaginativeLiteraturePage.clickRandomAuthor(2);
        List<String> selectedAuthorNames = imaginativeLiteraturePage.getClickedAuthorsList();
        RandomSearchResultsPage randomSearchResultsPage = searchResultsPage.moveToRandomPage();
        RandomBookPage randomBookPage = randomSearchResultsPage.clickOnRandomBookCard();
        String actualBookAuthor = randomBookPage.getBookAuthor();
        String bookName = randomBookPage.getBookName();
        Assert.assertTrue(selectedAuthorNames.contains(actualBookAuthor), String.format("Actual author of book `%s` - %s. Expected author %s", bookName, actualBookAuthor, selectedAuthorNames.toString()));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with only range first value")
    public void publicationYearFilterRangeFirstValue() {
        ImaginativeLiteraturePage imaginativeLiteraturePage = new HomePage(driver).open().chooseBookSection().chooseImaginativeLiteratureSection();
        int publicationYearRangeFirstValue = new Random().ints(2000, 2019 + 1).findFirst().getAsInt();
        SearchResultsPage searchResultsPage = imaginativeLiteraturePage.setPublicationYearFirstValue(Integer.toString(publicationYearRangeFirstValue)).applyPublicationYearFilter();
        RandomSearchResultsPage randomSearchResultsPage = searchResultsPage.moveToRandomPage();
        RandomBookPage randomBookPage = randomSearchResultsPage.clickOnRandomBookCard();
        int actualPublicationYear = randomBookPage.getBookPublicationYear();
        String bookName = randomBookPage.getBookName();
        Assert.assertTrue(actualPublicationYear >= publicationYearRangeFirstValue, String.format("Actual publication year of book + `%s` - %s. Expected year of publication %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeFirstValue)));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with only range last value")
    public void publicationYearFilterRangeLastValue() {
        ImaginativeLiteraturePage imaginativeLiteraturePage = new HomePage(driver).open().chooseBookSection().chooseImaginativeLiteratureSection();
        int publicationYearRangeLastValue = new Random().ints(2000, 2098 + 1).findFirst().getAsInt();
        SearchResultsPage searchResultsPage = imaginativeLiteraturePage.setPublicationYearLastValue(Integer.toString(publicationYearRangeLastValue)).applyPublicationYearFilter();
        RandomSearchResultsPage randomSearchResultsPage = searchResultsPage.moveToRandomPage();
        RandomBookPage randomBookPage = randomSearchResultsPage.clickOnRandomBookCard();
        int actualPublicationYear = randomBookPage.getBookPublicationYear();
        String bookName = randomBookPage.getBookName();
        Assert.assertTrue(actualPublicationYear <= publicationYearRangeLastValue, String.format("Actual publication year of book + `%s` - %s. Expected year of publication %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeLastValue)));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with full range")
    public void publicationYearFilterFullRange() {
        ImaginativeLiteraturePage imaginativeLiteraturePage = new HomePage(driver).open().chooseBookSection().chooseImaginativeLiteratureSection();
        int publicationYearRangeFirstValue = new Random().ints(2000, 2010 + 1).findFirst().getAsInt();
        int publicationYearRangeLastValue = new Random().ints(2010, 2098 + 1).findFirst().getAsInt();
        SearchResultsPage searchResultsPage = imaginativeLiteraturePage.setPublicationYearFirstValue(Integer.toString(publicationYearRangeFirstValue)).setPublicationYearLastValue(Integer.toString(publicationYearRangeLastValue)).applyPublicationYearFilter();
        RandomSearchResultsPage randomSearchResultsPage = searchResultsPage.moveToRandomPage();
        RandomBookPage randomBookPage = randomSearchResultsPage.clickOnRandomBookCard();
        int actualPublicationYear = randomBookPage.getBookPublicationYear();
        String bookName = randomBookPage.getBookName();
        Assert.assertTrue(publicationYearRangeFirstValue <= actualPublicationYear && actualPublicationYear <= publicationYearRangeLastValue, String.format("Actual publication year of book + `%s` - %s. Expected range of publication %s - %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeFirstValue), Integer.toString(publicationYearRangeLastValue)));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with full range")
    public void AuthorNameAndPublicationYearFilters() {
        ImaginativeLiteraturePage imaginativeLiteraturePage = new HomePage(driver).open().chooseBookSection().chooseImaginativeLiteratureSection();
        int publicationYearRangeFirstValue = new Random().ints(2000, 2010 + 1).findFirst().getAsInt();
        int publicationYearRangeLastValue = new Random().ints(2010, 2098 + 1).findFirst().getAsInt();
        imaginativeLiteraturePage.clickRandomAuthor(1);
        SearchResultsPage searchResultsPage = imaginativeLiteraturePage.setPublicationYearFirstValue(Integer.toString(publicationYearRangeFirstValue)).setPublicationYearLastValue(Integer.toString(publicationYearRangeLastValue)).applyPublicationYearFilter();
        List<String> selectedAuthorNames = imaginativeLiteraturePage.getClickedAuthorsList();
        RandomSearchResultsPage randomSearchResultsPage = searchResultsPage.moveToRandomPage();
        RandomBookPage randomBookPage = randomSearchResultsPage.clickOnRandomBookCard();
        String actualBookAuthor = randomBookPage.getBookAuthor();
        String bookName = new RandomBookPage(driver).getBookName();
        int actualPublicationYear = randomBookPage.getBookPublicationYear();
        SoftAssert twoFilters = new SoftAssert();
        twoFilters.assertTrue(publicationYearRangeFirstValue <= actualPublicationYear && actualPublicationYear <= publicationYearRangeLastValue, String.format("Actual publication year of book + `%s` - %s. Expected range of publication %s - %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeFirstValue), Integer.toString(publicationYearRangeLastValue)));
        twoFilters.assertTrue(selectedAuthorNames.contains(actualBookAuthor), String.format("Actual author of book `%s` - %s. Expected author %s", bookName, actualBookAuthor, selectedAuthorNames.toString()));
        twoFilters.assertAll();
    }

}
