package selenide.tests;

import com.codeborne.selenide.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


import selenide.service.JsonReader;
import selenide.tests.businessObjects.Book;
import selenide.tests.businessObjects.User;
import selenide.tests.pages.HomePage;
import selenide.tests.pages.ItemPage;
import selenide.tests.pages.SearchCriteria;
import selenide.tests.pages.SectionPage;
import com.codeborne.selenide.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.*;

public class FlipTestSelenide {

    private static final String URL = "https://flip.kz";
    private HomePage homePage;
    private int publicationYearRangeFirstValue;
    private int publicationYearRangeLastValue;
    private User user;

    @BeforeSuite
    public void initUserHomepage() {
        user = JsonReader.getUser();
        Configuration.browser="chrome";
        homePage = open(URL, HomePage.class);
        WebDriverRunner.getWebDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    @BeforeClass
    public void initPublicationYearRange() {
        publicationYearRangeFirstValue = JsonReader.getPublicationYearRangeFirstValue();
        publicationYearRangeLastValue = JsonReader.getPublicationYearRangeLastValue();
    }

    @BeforeMethod(alwaysRun = true)
    public void signIn() {
            homePage = homePage.signIn(user);
    }

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with one author")
    public void oneAuthor()  {
        SearchCriteria searchCriteria = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection();
        SectionPage bookPage = searchCriteria.clickRandomAuthor(1).moveToRandomPage();
        List<String> selectedAuthors = searchCriteria.getSelectedAuthorsList();
        ItemPage bookItemPage = bookPage.clickOnRandomBookCard();
        Book actualBook = new Book(bookItemPage.getBookName(), bookItemPage.getBookAuthors());
        Book expectedBook = new Book(selectedAuthors);
        Assert.assertTrue(actualBook.checkBooksEqualsByAuthorsList(expectedBook, actualBook), String.format("List of expected authors of book '%s' does not contain actual author. Expected authors list %s; actual authors list %s", actualBook.getName(), expectedBook.getAuthorNameList().toString(), actualBook.getAuthorNameList().toString()));
    }

}
