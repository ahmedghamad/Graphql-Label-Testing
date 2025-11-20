package steps;


import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.TestBase;
import io.restassured.response.Response;
import org.junit.Assert;
import utils.Config;


import java.io.IOException;
import java.util.Map;

public class ReadLabelStepdefs extends TestBase {

    private String labelDescribtion;
    private String color;

    private String labelId;
    private String labelName;
    private static Response createResponse;


    @Given("a label exists")
    public void aLabelExists() throws IOException {
        createResponse = executeQuery(
                readQuery("Readlabel.graphql"),
                "ReadLabel",
                Map.of(
                        "name", "Graphql-Label-Testing",
                        "owner", "ahmedghamad"
                )
        );

        labelName = createResponse.jsonPath().getString("repository.labels.nodes.name");
        labelId = createResponse.jsonPath().getString("repository.labels.nodes.id");
        labelDescribtion = createResponse.jsonPath().getString("repository.labels.nodes.id");
        color = createResponse.jsonPath().getString("repository.labels.nodes.color");

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
