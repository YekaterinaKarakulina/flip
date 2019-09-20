package selenide.tests.pages;

import com.codeborne.selenide.*;

import org.openqa.selenium.By;
import selenide.tests.businessObjects.User;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class HomePage extends BasePage {

    private static final String USER_INFO = "//*[contains(@class,'auth')]";
    private static final String SIGN_IN = "//*[contains(@class,'ath')]/*/a[contains(@href,'enter')]";
    private static final String EMAIL_INPUT = "//input[@id='email']";
    private static final String REGISTERED_FLAG = "//input[@id='is_reg']";
    private static final String PASSWORD_INPUT = "//input[@id='pass']";
    private static final String USER_NAME = "//*[contains(@class,'ath')]//a[contains(@href,'personalis')]/span";

    public MainMenuComponent getMainMenuComponent() {
        return page(MainMenuComponent.class);
    }

    public HomePage signIn(User user) {
        if (!$(By.xpath(USER_NAME)).getText().equals(user.getName())) {
            $(By.xpath(USER_INFO)).scrollTo().hover();
            clickToSelenideElement($(By.xpath(SIGN_IN)));
            $(By.xpath(EMAIL_INPUT)).setValue(user.getEmail());
            clickToSelenideElement($(By.xpath(REGISTERED_FLAG)));
            $(By.xpath(PASSWORD_INPUT)).setValue(user.getPassword()).pressEnter();
        }
        $(By.xpath(USER_NAME)).shouldHave(Condition.text(user.getName()));
        return page(HomePage.class);
    }

    public String getActualUserName() {
        return $(By.xpath(USER_NAME)).getText();
    }

}
