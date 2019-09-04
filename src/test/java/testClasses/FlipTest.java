package testClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testClasses.pages.HomePage;
import testClasses.pages.ItemPage;
import testClasses.pages.SectionPage;

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

    @AfterSuite
    public void afterSuite() {
        driver.close();
    }

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with one author")
    public void oneAuthorFilter() {
        HomePage homePage = new HomePage(driver).open();
        SectionPage bookPage = homePage.getMainMenuComponent().clickMenu().clickBookSection().clickImaginativeLiteratureSection().clickRandomAuthor(1).moveToRandomPage();
        List<String> selectedAuthors = bookPage.getBookFilter().getSelectedAuthorsList();
        ItemPage bookItemPage = bookPage.clickOnRandomBookCard();
        String actualBookAuthor = bookItemPage.getBookAuthor();
        String bookName = bookItemPage.getBookName();
        Assert.assertTrue(selectedAuthors.contains(actualBookAuthor), String.format("List of expected authors of book '%s' does not contain %s", bookName, actualBookAuthor));
    }

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with several author")
    public void severalAuthorsFilter() {
        HomePage homePage = new HomePage(driver).open();
        SectionPage bookPage = homePage.getMainMenuComponent().clickMenu().clickBookSection().clickImaginativeLiteratureSection().clickRandomAuthor(2).moveToRandomPage();
        List<String> selectedAuthors = bookPage.getBookFilter().getSelectedAuthorsList();
        ItemPage bookItemPage = bookPage.clickOnRandomBookCard();
        String actualBookAuthor = bookItemPage.getBookAuthor();
        String bookName = bookItemPage.getBookName();
        Assert.assertTrue(selectedAuthors.contains(actualBookAuthor), String.format("List of expected authors of book '%s' does not contain %s", bookName, actualBookAuthor));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with only range first value")
    public void publicationYearFilterRangeFirstValue() {
        HomePage homePage = new HomePage(driver).open();
        int publicationYearRangeFirstValue = new Random().ints(2000, 2019 + 1).findFirst().getAsInt();
        SectionPage bookPage = homePage.getMainMenuComponent().clickMenu().clickBookSection().clickImaginativeLiteratureSection().setPublicationYearFirstValue(Integer.toString(publicationYearRangeFirstValue)).applyPublicationYearFilter();
        ItemPage bookItemPage = bookPage.moveToRandomPage().clickOnRandomBookCard();
        int actualPublicationYear = bookItemPage.getBookPublicationYear();
        String bookName = bookItemPage.getBookName();
        Assert.assertTrue(actualPublicationYear >= publicationYearRangeFirstValue, String.format("Actual publication year of book + `%s` - %s. Expected year of publication %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeFirstValue)));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with only range last value")
    public void publicationYearFilterRangeLastValue() {
        HomePage homePage = new HomePage(driver).open();
        int publicationYearRangeLastValue = new Random().ints(2010, 2098 + 1).findFirst().getAsInt();
        SectionPage bookPage = homePage.getMainMenuComponent().clickMenu().clickBookSection().clickImaginativeLiteratureSection().setPublicationYearLastValue(Integer.toString(publicationYearRangeLastValue)).applyPublicationYearFilter();
        ItemPage bookItemPage = bookPage.moveToRandomPage().clickOnRandomBookCard();
        int actualPublicationYear = bookItemPage.getBookPublicationYear();
        String bookName = bookItemPage.getBookName();
        Assert.assertTrue(actualPublicationYear <= publicationYearRangeLastValue, String.format("Actual publication year of book + `%s` - %s. Expected year of publication %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeLastValue)));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with full range")
    public void publicationYearFilterFullRange() {
        HomePage homePage = new HomePage(driver).open();
        int publicationYearRangeFirstValue = new Random().ints(2000, 2010 + 1).findFirst().getAsInt();
        int publicationYearRangeLastValue = new Random().ints(2010, 2098 + 1).findFirst().getAsInt();
        SectionPage bookPage = homePage.getMainMenuComponent().clickMenu().clickBookSection().clickImaginativeLiteratureSection().setPublicationYearFirstValue(Integer.toString(publicationYearRangeFirstValue)).setPublicationYearLastValue(Integer.toString(publicationYearRangeLastValue)).applyPublicationYearFilter();
        ItemPage bookItemPage = bookPage.moveToRandomPage().clickOnRandomBookCard();
        int actualPublicationYear = bookItemPage.getBookPublicationYear();
        String bookName = bookItemPage.getBookName();
        Assert.assertTrue(publicationYearRangeFirstValue <= actualPublicationYear && actualPublicationYear <= publicationYearRangeLastValue, String.format("Actual publication year of book + `%s` - %s. Expected range of publication %s - %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeFirstValue), Integer.toString(publicationYearRangeLastValue)));
    }


    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with full range")
    public void AuthorNameAndPublicationYearFilters() {
        HomePage homePage = new HomePage(driver).open();
        SectionPage bookPageOneFilter = homePage.getMainMenuComponent().clickMenu().clickBookSection().clickImaginativeLiteratureSection().clickRandomAuthor(1);
        int publicationYearRangeFirstValue = new Random().ints(2000, 2010 + 1).findFirst().getAsInt();
        int publicationYearRangeLastValue = new Random().ints(2010, 2098 + 1).findFirst().getAsInt();
        List<String> selectedAuthors = bookPageOneFilter.getBookFilter().getSelectedAuthorsList();
        SectionPage bookPageTwoFilters = bookPageOneFilter.getBookFilter().setPublicationYearFirstValue(Integer.toString(publicationYearRangeFirstValue)).setPublicationYearLastValue(Integer.toString(publicationYearRangeLastValue)).applyPublicationYearFilter();
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
