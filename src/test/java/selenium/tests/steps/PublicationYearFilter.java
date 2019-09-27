package selenium.tests.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import selenium.tests.pages.HomePage;
import selenium.tests.pages.ItemPage;
import selenium.tests.pages.SearchCriteria;
import selenium.tests.pages.SectionPage;

public class PublicationYearFilter {
    private HomePage homePage;
    private SectionPage bookPage;
    private SearchCriteria searchCriteria;
    private ItemPage bookItemPage;



    @And("^User set publication year first value$")
    public void userSetPublicationYearFirstValue() {
        bookPage = searchCriteria.setPublicationYearFirstValue();
    }

    @And("^User moves to random result`s page, selects random book$")
    public void userMovesToRandomResultSPageSelectsRandomBook() {

    }

    @Then("^Random book`s publication year from result page is greater or equal then user entered$")
    public void randomBookSPublicationYearFromResultPageIsGreaterOrEqualThenUserEntered() {

    }
}
