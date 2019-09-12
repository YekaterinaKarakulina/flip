package tests.service.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class geckoDriverLinux {
    private static WebDriver driver = new FirefoxDriver();

    public static WebDriver geckoDriverLinux() {
        driver.manage().window().maximize();
        return driver;
    }
}
