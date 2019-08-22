import com.sun.org.glassfish.gmbal.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AuthorFilter {
    @Description("Test checks working of \"Authors\" filter in \"flip.kz\" website. " +
            "First, it goes to homepage to \"Книги, Художественная литература\" section. " +
            "Than it types author`s name to search books and check the results list. " +
            "It randomly choose one book from that list, and if its author`s name the same what was searched, test passed")
    @Test(dataProviderClass = DataProvider.class, dataProvider = "Authors")
    @Parameters({"name"})
    public void oneAuthorFilter(String name) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver76.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getToPage(driver, "https://flip.kz");
        findByXpathAndClick(driver, "//a[contains(@href,'1') and contains(text(), 'Книги')]");
        findByXpathAndClick(driver, "//a[@data-filter-field-sections-id='44']");
        findByXpathAndType(driver, "//div[@data-filter-field-list-type='peoples']/descendant-or-self::*[@placeholder='Введите название']", name);
        findByXpathAndClick(driver, String.format("%s%s%s", "//div[@data-filter-field-list-type='peoples']/descendant-or-self::*[contains(@data-list-found-name,'", name, "')]"));
        Thread.sleep(5000); //will change to some wait
        java.util.List<WebElement> listItems = driver.findElements(By.xpath("//div[@id='content']/descendant-or-self::*[contains(@class,'placeholder')]/descendant-or-self::*[contains(@class, 'pic')]"));
        Random random = new Random();
        int itemToCheck = random.nextInt(listItems.size());
        listItems.get(itemToCheck).click();
        String authorNameAtSite = driver.findElement(By.xpath("//p[contains(@style,'margin')]/descendant-or-self::*[contains(@href,'people')]")).getText();
        Assert.assertTrue(authorNameAtSite.contains(name));
        driver.close();
    }

    private static void getToPage(WebDriver driver, String URL) {
        driver.get(URL);
    }

    private static void findByXpathAndClick(WebDriver driver, String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    private static void findByXpathAndType(WebDriver driver, String xpath, String wordToType) {
        driver.findElement(By.xpath(xpath)).sendKeys(wordToType);
    }
}