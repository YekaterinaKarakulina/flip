package tests.service.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class chromeDriverWin {
    private static WebDriver driver = new ChromeDriver();

    public static WebDriver chromeDriverWin(){
        driver.manage().window().maximize();
        return driver;
    }

}
