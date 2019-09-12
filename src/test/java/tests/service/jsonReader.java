package tests.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import tests.bo.User;
import utils.RandomNumbersUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;


public class jsonReader {

    private static JsonObject parseJsonFile() {
        try {
            return (JsonObject) new JsonParser().parse(new FileReader("src/test/resources/data.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return parseJsonFile();
    }

    public static User getUser() {
        JsonElement dataSet = parseJsonFile().getAsJsonObject().get("Users");
        List<User> users = new Gson().fromJson(dataSet, new TypeToken<List<User>>() {
        }.getType());
        return users.get(RandomNumbersUtils.getUserNumber());
    }

    public static int getPublicationYearRangeFirstValue() {
        return Integer.parseInt(parseJsonFile().get("PublicationYearRangeFirstValue").toString());
    }

    public static int getPublicationYearRangeLastValue() {
        return Integer.parseInt(parseJsonFile().get("PublicationYearRangeLastValue").toString());
    }
}
