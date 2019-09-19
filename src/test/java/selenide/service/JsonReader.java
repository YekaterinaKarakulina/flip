package selenide.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import selenide.tests.businessObjects.User;
import selenide.utils.RandomNumbersUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class JsonReader {

    private static FileReader fileReader;

    static {
        try {
            fileReader = new FileReader("src/test/resources/data.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

}
