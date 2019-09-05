package testClasses;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testClasses.pages.BookFilter;
import testClasses.pages.HomePage;
import testClasses.pages.ItemPage;
import testClasses.pages.SectionPage;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class FlipTest {
    private WebDriver driver;
    private HomePage homePage;
    private int publicationYearRangeFirstValue;
    private int publicationYearRangeLastValue;

    @BeforeSuite
    public void initBrowser() {
        //System.setProperty("webdriver.gecko.driver", "src/test/java/resources/geckodriver"); //this for my home PC with linux
        //driver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "src/test/java/resources/chromedriver76.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeClass
    public void initializePublicationYearRange() {
        homePage = new HomePage(driver).open();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        publicationYearRangeFirstValue = new Random().ints(2000, 2010 + 1).findFirst().getAsInt();
        publicationYearRangeLastValue = new Random().ints(2010, currentYear + 1).findFirst().getAsInt();
    }

    @BeforeMethod
    public void openHomePage() {
        ((JavascriptExecutor) driver).executeScript("history.go(0)");
    }

    @AfterSuite
    public void afterSuite() {
        driver.close();
    }

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with one author")
    public void oneAuthorFilter() {
        BookFilter bookFilter = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection();
        SectionPage bookPage = bookFilter.clickRandomAuthor(1).moveToRandomPage();
        List<String> selectedAuthors = bookFilter.getSelectedAuthorsList();
        ItemPage bookItemPage = bookPage.clickOnRandomBookCard();
        String actualBookAuthor = bookItemPage.getBookAuthor();
        String bookName = bookItemPage.getBookName();
        Assert.assertTrue(selectedAuthors.contains(actualBookAuthor), String.format("List of expected authors of book '%s' does not contain %s", bookName, actualBookAuthor));
    }

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with several author")
    public void severalAuthorsFilter() {
        BookFilter bookFilter = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection();
        SectionPage bookPage = bookFilter.clickRandomAuthor(4).moveToRandomPage();
        List<String> selectedAuthors = bookFilter.getSelectedAuthorsList();
        ItemPage bookItemPage = bookPage.clickOnRandomBookCard();
        String actualBookAuthor = bookItemPage.getBookAuthor();
        String bookName = bookItemPage.getBookName();
        Assert.assertTrue(selectedAuthors.contains(actualBookAuthor), String.format("List of expected authors of book '%s' does not contain %s", bookName, actualBookAuthor));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with only range first value")
    public void publicationYearFilterRangeFirstValue() {
        SectionPage bookPage = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection().setPublicationYearFirstValue();
        int publicationYearRangeFirstValue = bookPage.getBookFilter().getExpectedPublicationYearFirstValue();
        ItemPage bookItemPage = bookPage.moveToRandomPage().clickOnRandomBookCard();
        int actualPublicationYear = bookItemPage.getBookPublicationYear();
        String bookName = bookItemPage.getBookName();
        Assert.assertTrue(actualPublicationYear >= publicationYearRangeFirstValue, String.format("Actual publication year of book + `%s` - %s. Expected year of publication %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeFirstValue)));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with only range last value")
    public void publicationYearFilterRangeLastValue() {
        SectionPage bookPage = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection().setPublicationYearLastValue();
        int publicationYearRangeLastValue = bookPage.getBookFilter().getExpectedPublicationYearLastValue();
        ItemPage bookItemPage = bookPage.moveToRandomPage().clickOnRandomBookCard();
        int actualPublicationYear = bookItemPage.getBookPublicationYear();
        String bookName = bookItemPage.getBookName();
        Assert.assertTrue(actualPublicationYear <= publicationYearRangeLastValue, String.format("Actual publication year of book + `%s` - %s. Expected year of publication %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeLastValue)));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with full range")
    public void publicationYearFilterFullRange() {
        SectionPage bookPage = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection().setPublicationYearFilter(Integer.toString(publicationYearRangeFirstValue), Integer.toString(publicationYearRangeLastValue));
        ItemPage bookItemPage = bookPage.moveToRandomPage().clickOnRandomBookCard();
        int actualPublicationYear = bookItemPage.getBookPublicationYear();
        String bookName = bookItemPage.getBookName();
        Assert.assertTrue(publicationYearRangeFirstValue <= actualPublicationYear && actualPublicationYear <= publicationYearRangeLastValue, String.format("Actual publication year of book + `%s` - %s. Expected range of publication %s - %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeFirstValue), Integer.toString(publicationYearRangeLastValue)));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with full range")
    public void AuthorNameAndPublicationYearFilters() {
        BookFilter bookFilter = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection();
        SectionPage bookPage = bookFilter.clickRandomAuthor(1).moveToRandomPage();
        List<String> selectedAuthors = bookFilter.getSelectedAuthorsList();
        SectionPage bookPageTwoFilters = bookPage.getBookFilter().setPublicationYearFilter(Integer.toString(publicationYearRangeFirstValue), Integer.toString(publicationYearRangeLastValue));
        ItemPage bookItemPage = bookPageTwoFilters.moveToRandomPage().clickOnRandomBookCard();
        String actualBookAuthor = bookItemPage.getBookAuthor();
        String bookName = bookItemPage.getBookName();
        int actualPublicationYear = bookItemPage.getBookPublicationYear();
        SoftAssert twoFilters = new SoftAssert();
        twoFilters.assertTrue(publicationYearRangeFirstValue <= actualPublicationYear && actualPublicationYear <= publicationYearRangeLastValue, String.format("Actual publication year of book + `%s` - %s. Expected range of publication %s - %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeFirstValue), Integer.toString(publicationYearRangeLastValue)));
        twoFilters.assertTrue(selectedAuthors.contains(actualBookAuthor), String.format("List of expected authors of book '%s' does not contain %s", bookName, actualBookAuthor));
        twoFilters.assertAll();
    }
}
