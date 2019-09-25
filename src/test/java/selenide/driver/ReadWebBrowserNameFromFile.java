package selenide.driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadWebBrowserNameFromFile {

    public static String readDriver() {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/driverSelenide.properties");
            Properties prop = new Properties();
            prop.load(fis);
            return prop.getProperty("browser");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "firefoxWin";
    }
}





