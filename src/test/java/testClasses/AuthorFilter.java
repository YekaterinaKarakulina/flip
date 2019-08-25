package testClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Random;


public class AuthorFilter extends BaseTest {
    @Test(description = "Test checks working of \"Authors\" filter in \"flip.kz\" website with one author", dataProviderClass = DataProvider.class, dataProvider = "dp")
    @Parameters({"name"})
    public void oneAuthorFilter(String name) throws InterruptedException {


        findByXpathAndType(getDriver(), "//div[@data-filter-field-list-type='peoples']/descendant-or-self::*[@placeholder='Введите название']", name);
        findByXpathAndClick(getDriver(), String.format("%s%s%s", "//div[@data-filter-field-list-type='peoples']/descendant-or-self::*[contains(@data-list-found-name,'", name, "')]"));
        Thread.sleep(1000); //will change to some wait

        java.util.List<WebElement> listItems = getDriver().findElements(By.xpath("//div[@id='content']/descendant-or-self::*[contains(@class,'placeholder')]/descendant-or-self::*[contains(@class, 'pic')]"));
        Random random = new Random();
        int itemToCheck = random.nextInt(listItems.size());
        listItems.get(itemToCheck).click();
        String authorNameAtSite = getDriver().findElement(By.xpath("//p[contains(@style,'margin')]/descendant-or-self::*[contains(@href,'people')]")).getText();
        Assert.assertTrue(authorNameAtSite.contains(name));
    }


}

