package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.bo.Book;
import tests.bo.User;
import tests.pages.SearchCriteria;
import tests.pages.HomePage;
import tests.pages.ItemPage;
import tests.pages.SectionPage;
import tests.service.jsonReader;
import tests.service.chooseBrowser;

import java.util.List;


public class FlipTest {
    private WebDriver driver;
    private HomePage homePage;
    private int publicationYearRangeFirstValue;
    private int publicationYearRangeLastValue;
    private User user;

    @BeforeSuite
    public WebDriver initBrowser() {
        driver = chooseBrowser.initBrowser();
        return driver;
    }

    @BeforeSuite
    public User initUser() {
        return user = jsonReader.getUser();
    }

    @BeforeClass
    public void initializePublicationYearRange() {
        publicationYearRangeFirstValue = jsonReader.getPublicationYearRangeFirstValue();
        publicationYearRangeLastValue = jsonReader.getPublicationYearRangeLastValue();
    }

    @AfterSuite
    public void afterSuite() {
        driver.close();
    }

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with one author", priority = 1)
    public void oneAuthorFilter() {
        homePage = new HomePage(driver).open().signIn(user);
        SearchCriteria searchCriteria = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection();
        SectionPage bookPage = searchCriteria.clickRandomAuthor(1).moveToRandomPage();
        List<String> selectedAuthors = searchCriteria.getSelectedAuthorsList();
        ItemPage bookItemPage = bookPage.clickOnRandomBookCard();
        Book actualBook = new Book();
        actualBook.setName(bookItemPage.getBookName());
        actualBook.setAuthorName(bookItemPage.getBookAuthor());
        Book expectedBook = new Book();
        expectedBook.setAuthorName(selectedAuthors.get(0));
        System.out.println("act a " + actualBook.getAuthorName() + "exp a " + expectedBook.getAuthorName());
        Assert.assertTrue(expectedBook.getAuthorName().equals(actualBook.getAuthorName()), String.format("Expected author of book '%s' - %s, actual author %s", actualBook.getName(), expectedBook.getAuthorName(), actualBook.getAuthorName()));
        //String actualBookAuthor = bookItemPage.getBookAuthor();
        // String bookName = bookItemPage.getBookName();
        //Assert.assertTrue(selectedAuthors.contains(actualBookAuthor), String.format("List of expected authors of book '%s' does not contain %s", bookName, actualBookAuthor));
    }

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with several author", priority = 1)
    public void severalAuthorsFilter() {
        homePage = new HomePage(driver).open().signIn(user);
        SearchCriteria searchCriteria = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection();
        SectionPage bookPage = searchCriteria.clickRandomAuthor(3).moveToRandomPage();
        List<String> selectedAuthors = searchCriteria.getSelectedAuthorsList();
        ItemPage bookItemPage = bookPage.clickOnRandomBookCard();
        String actualBookAuthor = bookItemPage.getBookAuthor();
        String bookName = bookItemPage.getBookName();
        Assert.assertTrue(selectedAuthors.contains(actualBookAuthor), String.format("List of expected authors of book '%s' does not contain %s", bookName, actualBookAuthor));
    }
    
    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with only range first value", priority = 1)
    public void publicationYearFilterRangeFirstValue() {
        homePage = new HomePage(driver).open().signIn(user);
        SectionPage bookPage = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection().setPublicationYearFirstValue();
        int publicationYearRangeFirstValue = bookPage.getBookFilter().getExpectedPublicationYearFirstValue();
        ItemPage bookItemPage = bookPage.moveToRandomPage().clickOnRandomBookCard();
        int actualPublicationYear = bookItemPage.getBookPublicationYear();
        String bookName = bookItemPage.getBookName();
        Assert.assertTrue(actualPublicationYear >= publicationYearRangeFirstValue, String.format("Actual publication year of book + `%s` - %s. Expected year of publication %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeFirstValue)));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with only range last value", priority = 1)
    public void publicationYearFilterRangeLastValue() {
        homePage = new HomePage(driver).open().signIn(user);
        SectionPage bookPage = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection().setPublicationYearLastValue();
        int publicationYearRangeLastValue = bookPage.getBookFilter().getExpectedPublicationYearLastValue();
        ItemPage bookItemPage = bookPage.moveToRandomPage().clickOnRandomBookCard();
        int actualPublicationYear = bookItemPage.getBookPublicationYear();
        String bookName = bookItemPage.getBookName();
        Assert.assertTrue(actualPublicationYear <= publicationYearRangeLastValue, String.format("Actual publication year of book + `%s` - %s. Expected year of publication %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeLastValue)));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with full range", priority = 1)
    public void publicationYearFilterFullRange() {
        homePage = new HomePage(driver).open().signIn(user);
        SectionPage bookPage = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection().setPublicationYearFilter(Integer.toString(publicationYearRangeFirstValue), Integer.toString(publicationYearRangeLastValue));
        ItemPage bookItemPage = bookPage.moveToRandomPage().clickOnRandomBookCard();
        int actualPublicationYear = bookItemPage.getBookPublicationYear();
        String bookName = bookItemPage.getBookName();
        Assert.assertTrue(publicationYearRangeFirstValue <= actualPublicationYear && actualPublicationYear <= publicationYearRangeLastValue, String.format("Actual publication year of book + `%s` - %s. Expected range of publication %s - %s", bookName, Integer.toString(actualPublicationYear), Integer.toString(publicationYearRangeFirstValue), Integer.toString(publicationYearRangeLastValue)));
    }

    @Test(description = "Test checks working of \"Publication Year\" filter in \"flip.kz\" website with full range", priority = 1)
    public void AuthorNameAndPublicationYearFilters() {
        homePage = new HomePage(driver).open().signIn(user);
        SearchCriteria searchCriteria = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection();
        SectionPage bookPage = searchCriteria.clickRandomAuthor(1).moveToRandomPage();
        List<String> selectedAuthors = searchCriteria.getSelectedAuthorsList();
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
