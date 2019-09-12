package tests.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class driverReader {
    public static String readDriver() {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/driver.properties");
            Properties prop = new Properties();
            prop.load(fis);
            System.out.println(prop.getProperty("browser"));
            return prop.getProperty("browser");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readDriver();
    }
}
