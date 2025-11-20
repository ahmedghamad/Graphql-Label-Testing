package steps;


import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.TestBase;

import java.util.Map;

public class ReadLabelStepdefs extends TestBase {
    @Given("a label exists")
    public void aLabelExists() {
        executeQuery(
                readQuery("Readlabel.graphql"),
                "ReadLabel",
                Map.of(
                        "name", "Graphql-Label-Testing",
                        "owner", "ahmedghamad"
                )
        )

    }

    @When("I want to be able to get issues with labels")
    public void iWantToBeAbleToGetIssuesWithLabels() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("So that I can see which labels are being used")
    public void soThatICanSeeWhichLabelsAreBeingUsed() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
