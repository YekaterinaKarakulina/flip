package selenium.tests.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import selenium.driver.WebDriverManager;
import selenium.service.FileReaderJsonAndProperties;
import selenium.tests.businessObjects.Book;
import selenium.tests.businessObjects.User;
import selenium.tests.pages.HomePage;
import selenium.tests.pages.ItemPage;
import selenium.tests.pages.SearchCriteria;
import selenium.tests.pages.SectionPage;

import java.util.List;

public class OneAuthor {
    private WebDriver driver;
    private HomePage homePage;
    private SearchCriteria searchCriteria;
    private  ItemPage bookItemPage;
    private User user;
    private List<String> selectedAuthors;

    @Given("^Website flip\\.kz is opened$")
    public void websiteFlipKzIsOpened() {
        driver = WebDriverManager.getWebDriverInstance();
        user = FileReaderJsonAndProperties.getUser();
        homePage = new HomePage(driver).open();
    }

    @When("^User enters email and login in input fields$")
    public void userEntersEmailAndLoginInInputFields() {
        homePage = homePage.signIn(user);
    }

    @Then("^User logged in$")
    public void userLoggedIn() {
        Assert.assertEquals(homePage.getActualUserName(), user.getName(), String.format("SignIn error. Expected user name - %s, actual - %s.", user.getName(), homePage.getActualUserName()));
    }


    @Given("^User is authorized$")
    public void userIsAuthorized() {

    }

    @When("^User navigates to imaginative literature section$")
    public void userNavigatesToImaginativeLiteratureSection() {
        searchCriteria = homePage.getMainMenuComponent().clickBookSection().clickImaginativeLiteratureSection();
    }

    @And("^User selects random book author, moves to random result`s page, selects random book$")
    public void userSelectsRandomBookAuthorMovesToRandomResultSPageSelectsRandomBook() {
        SectionPage bookPage = searchCriteria.clickRandomAuthor(1).moveToRandomPage();
        selectedAuthors = searchCriteria.getSelectedAuthorsList();
        bookItemPage = bookPage.clickOnRandomBookCard();
    }

    @Then("^Random book`s author from result page is selected author$")
    public void randomBookSAuthorFromResultPageIsSelectedAuthor() {
        Book actualBook = new Book(bookItemPage.getBookName(), bookItemPage.getBookAuthors());
        Book expectedBook = new Book(selectedAuthors);
        Assert.assertTrue(actualBook.checkBooksEqualsByAuthorsList(expectedBook, actualBook), String.format("List of expected authors of book '%s' does not contain actual author. Expected authors list %s; actual authors list %s.", actualBook.getName(), expectedBook.getAuthorNameList().toString(), actualBook.getAuthorNameList().toString()));
    }

}
