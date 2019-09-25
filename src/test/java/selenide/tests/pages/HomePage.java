package selenide.tests.pages;

import com.codeborne.selenide.*;

import org.openqa.selenium.By;
import selenide.tests.businessObjects.User;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class HomePage extends BasePage {

    private static final By USER_INFO = By.xpath("//*[contains(@class,'auth')]");
    private static final By SIGN_IN = By.xpath("//*[contains(@class,'ath')]/*/a[contains(@href,'enter')]");
    private static final By EMAIL_INPUT = By.xpath("//input[@id='email']");
    private static final By REGISTERED_FLAG = By.xpath("//input[@id='is_reg']");
    private static final By PASSWORD_INPUT = By.xpath("//input[@id='pass']");
    private static final By USER_NAME = By.xpath("//*[contains(@class,'ath')]//a[contains(@href,'personalis')]/span");

    public MainMenuComponent getMainMenuComponent() {
        return page(MainMenuComponent.class);
    }

    public HomePage signIn(User user) {
        if (!$(USER_NAME).getText().equals(user.getName())) {
            $(USER_INFO).scrollTo().hover();
            clickToSelenideElement($(SIGN_IN));
            $(EMAIL_INPUT).setValue(user.getEmail());
            clickToSelenideElement($(REGISTERED_FLAG));
            $(PASSWORD_INPUT).setValue(user.getPassword()).pressEnter();
        }
        $(USER_NAME).shouldHave(Condition.text(user.getName()));
        return this;
    }

    public String getActualUserName() {
        return $(USER_NAME).text();
    }

}
