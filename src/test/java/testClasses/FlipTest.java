package testClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import testClasses.pages.HomePage;
import testClasses.pages.ImaginativeLiteraturePage;

public class FlipTest {
    private WebDriver driver;

    @BeforeSuite
    public void initBrowser() {
        //System.setProperty("webdriver.gecko.driver", "src/test/java/resources/geckodriver"); //this for my home PC with linux
        //driver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "src/test/java/resources/chromedriver76.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with one author")
    public void oneAuthorFilter(){
        ImaginativeLiteraturePage imaginativeLiteraturePage = new HomePage(driver).open().chooseBookSection().chooseImaginativeLiteratureSection();
    }
}
