package testClasses;

public class DataProvider {
    @org.testng.annotations.DataProvider(name = "dp")
    public Object[][] dataProviderString() {
        return new Object[][]{
                {"Пушкин"},
                {"Гоголь"},
                {"Кинг"}
        };
    }
}
