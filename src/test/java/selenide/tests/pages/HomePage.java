package selenide.tests.pages;

import com.codeborne.selenide.*;

import org.openqa.selenium.By;
import selenide.tests.businessObjects.User;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class HomePage extends BasePage {

    private static final String userInfo = "//*[contains(@class,'auth')]";
    private static final String signIn = "//*[contains(@class,'ath')]/*/a[contains(@href,'enter')]";
    private static final String emailInput = "//input[@id='email']";
    private static final String registeredFlag = "//input[@id='is_reg']";
    private static final String passwordInput = "//input[@id='pass']";
    private static final String userName = "//*[contains(@class,'ath')]//a[contains(@href,'personalis')]/span";

    public MainMenuComponent getMainMenuComponent() {
        return page(MainMenuComponent.class);
    }

    public HomePage open(String url) {
        open(url);
        return page(HomePage.class);
    }

    public HomePage signIn(User user) {
        if (!$(By.xpath(userName)).equals(user.getName())) {
            $(By.xpath(userInfo)).hover();
            clickToSelenideElement($(By.xpath(signIn)));
            $(By.xpath(emailInput)).setValue(user.getEmail());
            clickToSelenideElement($(By.xpath(registeredFlag)));
            $(By.xpath(passwordInput)).setValue(user.getPassword()).pressEnter();
        }
        $(By.xpath(userName)).shouldHave(Condition.text(user.getName()));
        return page(HomePage.class);
    }

    public String getActualUserName() {
        return $(By.xpath(userName)).getText();
    }

}
