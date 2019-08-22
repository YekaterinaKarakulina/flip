public class DataProvider {
    @org.testng.annotations.DataProvider(name = "Authors")
    public Object[][] dataProviderString() {
        return new Object[][]{
                {"Пушкин"},
                {"Гоголь"},
                {"Кинг"}
        };
    }
}
