package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.businessObjects.Book;
import tests.businessObjects.User;
import tests.pages.SearchCriteria;
import tests.pages.HomePage;
import tests.pages.ItemPage;
import tests.pages.SectionPage;
import service.JsonReader;
import driver.InitWebBrowser;

import java.util.List;

public class FlipTest {

    private WebDriver driver;
    private HomePage homePage;
    private int publicationYearRangeFirstValue;
    private int publicationYearRangeLastValue;
    private User user;

    @BeforeSuite
    public void initBrowserUserHomepage() {
        driver = InitWebBrowser.initBrowser();
        user = JsonReader.getUser();
        homePage = new HomePage(driver).open();
    }

    @BeforeClass
    public void initPublicationYearRange() {
        publicationYearRangeFirstValue = JsonReader.getPublicationYearRangeFirstValue();
        publicationYearRangeLastValue = JsonReader.getPublicationYearRangeLastValue();
    }

    @BeforeMethod(alwaysRun = true)
    public void signIn() {
        homePage = homePage.signIn(user).validateActualUser(user.getName());
    }

    @AfterSuite
    public void afterSuite() {
        driver.close();
    }

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with one author")
    public void oneAuthorFilter() {
        SearchCriteria searchCriteria = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection();
        SectionPage bookPage = searchCriteria.clickRandomAuthor(1).moveToRandomPage();
        List<String> selectedAuthors = searchCriteria.getSelectedAuthorsList();
        ItemPage bookItemPage = bookPage.clickOnRandomBookCard();
        Book actualBook = new Book(bookItemPage.getBookName(), bookItemPage.getBookAuthors());
        Book expectedBook = new Book(selectedAuthors);
        Assert.assertTrue(actualBook.checkBooksEqualsByAuthorsList(expectedBook, actualBook), String.format("List of expected authors of book '%s' does not contain actual author. Expected authors list %s; actual authors list %s", actualBook.getName(), expectedBook.getAuthorNameList().toString(), actualBook.getAuthorNameList().toString()));
    }

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with several author")
    public void severalAuthorsFilter() {
        SearchCriteria searchCriteria = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection();
        SectionPage bookPage = searchCriteria.clickRandomAuthor(3).moveToRandomPage();
        List<String> selectedAuthors = searchCriteria.getSelectedAuthorsList();
        ItemPage bookItemPage = bookPage.clickOnRandomBookCard();
        List<String> actualAuthors = bookItemPage.getBookAuthors();
        String bookName = bookItemPage.getBookName();
        Assert.assertTrue(actualAuthors.stream().anyMatch(selectedAuthors::contains), String.format("List of expected authors of book '%s' does not contain actual author. Expected authors list %s; actual authors list %s", bookName, selectedAuthors.toString(), actualAuthors.toString()));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with only range first value")
    public void publicationYearFilterRangeFirstValue() {
        SectionPage bookPage = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection().setPublicationYearFirstValue();
        int publicationYearRangeFirstValue = bookPage.getSearchCriteria().getExpectedPublicationYearFirstValue();
        System.out.println("first " + publicationYearRangeFirstValue);
        ItemPage bookItemPage = bookPage.moveToRandomPage().clickOnRandomBookCard();
        String bookName = bookItemPage.getBookName();
        int actualPublicationYear = bookItemPage.getBookPublicationYear();
        Assert.assertTrue(actualPublicationYear >= publicationYearRangeFirstValue, String.format("Actual publication year of book + `%s` - %s. Expected year of publication %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeFirstValue)));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with only range last value")
    public void publicationYearFilterRangeLastValue() {
        SectionPage bookPage = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection().setPublicationYearLastValue();
        int publicationYearRangeLastValue = bookPage.getSearchCriteria().getExpectedPublicationYearLastValue();
        ItemPage bookItemPage = bookPage.moveToRandomPage().clickOnRandomBookCard();
        String bookName = bookItemPage.getBookName();
        int actualPublicationYear = bookItemPage.getBookPublicationYear();
        Assert.assertTrue(actualPublicationYear <= publicationYearRangeLastValue, String.format("Actual publication year of book `%s` - %s. Expected year of publication %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeLastValue)));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with full range")
    public void publicationYearFilterFullRange() {
        SectionPage bookPage = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection().setPublicationYearFilter(Integer.toString(publicationYearRangeFirstValue), Integer.toString(publicationYearRangeLastValue));
        ItemPage bookItemPage = bookPage.moveToRandomPage().clickOnRandomBookCard();
        String bookName = bookItemPage.getBookName();
        int actualPublicationYear = bookItemPage.getBookPublicationYear();
        Assert.assertTrue(publicationYearRangeFirstValue <= actualPublicationYear && actualPublicationYear <= publicationYearRangeLastValue, String.format("Actual publication year of book `%s` - %s. Expected range of publication %s - %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeFirstValue), Integer.toString(publicationYearRangeLastValue)));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with full range")
    public void AuthorNameAndPublicationYearFilters() {
        SearchCriteria searchCriteria = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection();
        SectionPage bookPage = searchCriteria.clickRandomAuthor(1).moveToRandomPage();
        List<String> selectedAuthors = searchCriteria.getSelectedAuthorsList();
        SectionPage bookPageTwoFilters = bookPage.getSearchCriteria().setPublicationYearFilter(Integer.toString(publicationYearRangeFirstValue), Integer.toString(publicationYearRangeLastValue));
        ItemPage bookItemPage = bookPageTwoFilters.moveToRandomPage().clickOnRandomBookCard();
        int actualPublicationYear = bookItemPage.getBookPublicationYear();
        Book actualBook = new Book(bookItemPage.getBookName(), bookItemPage.getBookAuthors());
        Book expectedBook = new Book(selectedAuthors);
        SoftAssert twoFilters = new SoftAssert();
        twoFilters.assertTrue(publicationYearRangeFirstValue <= actualPublicationYear && actualPublicationYear <= publicationYearRangeLastValue, String.format("Actual publication year of book `%s` - %s. Expected range of publication %s - %s", actualBook.getName(), Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeFirstValue), Integer.toString(publicationYearRangeLastValue)));
        twoFilters.assertTrue(actualBook.checkBooksEqualsByAuthorsList(expectedBook, actualBook), String.format("List of expected authors of book '%s' does not contain actual author. Expected authors list %s; actual authors list %s", actualBook.getName(), expectedBook.getAuthorNameList().toString(), actualBook.getAuthorNameList().toString()));
        twoFilters.assertAll();
    }

}
