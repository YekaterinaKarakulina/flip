package tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import tests.businessObjects.User;

public class HomePage extends BasePage {

    private static final String URL = "https://flip.kz";

    @FindBy(xpath = "//a/span[contains(text(),'Войти')]")
    private WebElement signIn;

    @FindBy(xpath = "//input[@id='email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@id='is_reg']")
    private WebElement registeredFlag;

    @FindBy(xpath = "//input[@id='pass']")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[@id='enter_button']")
    private WebElement enterButton;

    @FindBy(xpath = "//div[contains(@class,'ath')]//span[@class='p500']")
    private WebElement userName;

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
            clickToWebElement(signIn);
            sendKeysToWebElement(emailInput, user.getEmail());
            clickToWebElement(registeredFlag);
            sendKeysToWebElement(passwordInput, user.getPassword());
            clickToWebElement(enterButton);
        }
        waitForElementEnabled(userName);
        waitUntilElementHasText(userName, user.getName());
        return this;
    }

    public String getActualUserName() {
        return userName.getText();
    }

}
