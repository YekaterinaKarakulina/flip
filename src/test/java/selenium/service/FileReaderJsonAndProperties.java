package selenium.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import selenium.reporting.MyLogger;
import selenium.tests.businessObjects.User;
import selenium.utils.RandomNumbersUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class FileReaderJsonAndProperties {

    private static java.io.FileReader fileReader;

    static {
        try {
            fileReader = new java.io.FileReader("src/test/resources/data.json");
        } catch (FileNotFoundException e) {
            MyLogger.error(e.getMessage(), e);
        }
    }

    private static JsonParser jsonParser = new JsonParser();
    private static JsonObject jsonObject = (JsonObject) jsonParser.parse(fileReader);

    public static User getUser() {
        JsonElement dataSet = jsonObject.getAsJsonObject().get("Users");
        List<User> users = new Gson().fromJson(dataSet, new TypeToken<List<User>>() {
        }.getType());
        return users.get(RandomNumbersUtils.getRandomNumber(0, 1));
    }

    public static int getPublicationYearRangeFirstValue() {
        return Integer.parseInt(jsonObject.get("PublicationYearRangeFirstValue").toString());
    }

    public static int getPublicationYearRangeLastValue() {
        return Integer.parseInt(jsonObject.get("PublicationYearRangeLastValue").toString());
    }

    public static String readDriver(String elementToRead) {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/driverSelenium.properties");
            Properties prop = new Properties();
            prop.load(fis);
            return prop.getProperty(elementToRead);
        } catch (IOException e) {
            MyLogger.error(e.getMessage(), e);
        }
        MyLogger.error("WebDriver type or name incorrect. Go to `driverSelenium.properties` file and change properties");
        throw new RuntimeException("WebDriver type or name incorrect. Go to `driverSelenium.properties` file and change properties");
    }

}
