package testClasses.pages;

import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private static final String URL = "https://flip.kz";

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
}
