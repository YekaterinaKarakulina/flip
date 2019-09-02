package testClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import testClasses.pages.HomePage;
import testClasses.pages.ImaginativeLiteraturePage;
import testClasses.pages.RandomBookPage;
import testClasses.pages.RandomSearchResultsPage;
import testClasses.pages.SearchResultsPage;

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
        String expectedBookAuthor = new ImaginativeLiteraturePage(driver).chooseRandomAuthor(new ImaginativeLiteraturePage(driver).getAuthorsList());
        SearchResultsPage searchResultsPage = new ImaginativeLiteraturePage(driver).clickRandomAuthor(expectedBookAuthor);
        RandomSearchResultsPage randomSearchResultsPage = new SearchResultsPage(driver).moveToRandomPage();
        RandomBookPage randomBookPage = new RandomSearchResultsPage(driver).clickOnRandomBookCard();
        String actualBookAuthor = new RandomBookPage(driver).getBookAuthor();
        String bookName = new RandomBookPage(driver).getBookName();
        Assert.assertEquals(expectedBookAuthor, actualBookAuthor, String.format("Actual author of book `%s` - %s. Expected author %s", bookName, actualBookAuthor, expectedBookAuthor));
    }
}
