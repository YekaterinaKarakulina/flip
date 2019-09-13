package tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import tests.businessObjects.User;

public class HomePage extends BasePage {

    private static final String URL = "https://flip.kz";

    @FindBy(xpath = "//a/span[contains(text(),'Войти')]")
    private WebElement signUp;

    @FindBy(xpath = "//input[@id='email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@id='is_reg']")
    private WebElement registeredFlag;

    @FindBy(xpath = "//input[@id='pass']")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[@id='enter_button']")
    private WebElement enterButton;

    @FindBy(xpath = "//div[contains(@class,'ath')]//span[@class='p500']")
    private static WebElement userName;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public MainMenuComponent getMainMenuComponent() {
        return new MainMenuComponent(getDriver());
    }

    public HomePage open() {
        getDriver().get(URL);
        return this;
    }

    public HomePage signIn(User user) {
        if (!userName.getText().equals(user.getName())) {
            clickToWebElement(signUp);
            sendKeysToWebElement(emailInput, user.getEmail());
            clickToWebElement(registeredFlag);
            sendKeysToWebElement(passwordInput, user.getPassword());
            clickToWebElement(enterButton);
        }
        waitForElementEnabled(userName);
        return this;
    }

    public static String getActualUserName() {
        return userName.getText();
    }

}
