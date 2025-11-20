package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utils.TestBase;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.util.Map;

public class UpdateLabelStepdefs extends TestBase {


    private static String Label_ID;

    private static String newTitle;
    private static String newBody;
    private static String newColour;

    private static Response response;


    @Given("an label exists for update")
    public void anLabelExistsForUpdate() {
        Label_ID = "LA_kwDOQY0lA88AAAACQMVKAA";
    }

    @When("I update the issue")
    public void iUpdateTheIssue() throws IOException {
        newTitle = "bug 2";
        newBody = "Something isn't working";
        newColour = "#d73a4a";

        response = TestBase.executeQuery(
                TestBase.readQuery("DeleteIssueById.graphql"),
                "DeleteIssue",
                Map.of(
                        "id", Label_ID,
                        "description", newTitle,
                        "body", newBody,
                        "color", newColour
                )
        );
    }

    @Then("I should see the updated title")
    public void iShouldSeeTheUpdatedTitle() {
        assertThat(response.statusCode(), is(200));
    }

    @And("I should return the label to it's original state")
    public void iShouldReturnTheLabelToItSOriginalState() throws IOException {
        newTitle = "bug";
        newBody = "Something isn't working";
        newColour = "#d73a4a";
        response = TestBase.executeQuery(
                TestBase.readQuery("DeleteIssueById.graphql"),
                "DeleteIssue",
                Map.of(
                        "id", Label_ID,
                        "description", newTitle,
                        "body", newBody,
                        "color", newColour
                )
        );
    }
}
