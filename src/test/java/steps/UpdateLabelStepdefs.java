package steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utils.TestBase;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class UpdateLabelStepdefs extends TestBase {


    private static String Label_ID;

    private static String newTitle;
    private static String newBody;
    private static String newColour;

    private static Response response;


    @Given("an label exists for update")
    public void anLabelExistsForUpdate() {
        Label_ID = "LA_kwDOQY0lA88AAAACQMVKUw";
    }

    @When("I update the label")
    public void iUpdateTheLabel() throws IOException {
        newTitle = "wontfix 2";
        newBody = "This will not be worked on";
        newColour = "ffffff";

        response = TestBase.executeQuery(
                TestBase.readQuery("UpdateLabel.graphql"),
                "UpdateLabel",
                Map.of(
                        "id", Label_ID,
                        "name", newTitle,
                        "description", newBody,
                        "color", newColour
                )
        );
    }

    @Then("I should see the updated title")
    public void iShouldSeeTheUpdatedTitle() {
        assertThat(response.jsonPath().getString("data.updateLabel.label.name"), is(newTitle));
    }

    @And("I should return the label to it's original state")
    public void iShouldReturnTheLabelToItSOriginalState() throws IOException {
        newTitle = "wontfix";
        newBody = "This will not be worked on";
        newColour = "ffffff";
        response = TestBase.executeQuery(
                TestBase.readQuery("UpdateLabel.graphql"),
                "UpdateLabel",
                Map.of(
                        "id", Label_ID,
                        "name", newTitle,
                        "description", newBody,
                        "color", newColour
                )
        );
    }

    @Then("I should a status code of {int}")
    public void iShouldAStatusCodeOf(int arg0) {
        assertThat(response.statusCode(), is(200));
    }

    @And("I should see the no error messages")
    public void iShouldSeeTheNoErrorMessages() {
        assertThat(response.jsonPath().getList("errors"), is(nullValue()));
    }

    @And("I should see the updated body")
    public void iShouldSeeTheUpdatedBody() {
        assertThat(response.jsonPath().getString("data.updateLabel.label.description"), is(newBody));
    }


    @And("I should see it has been updated today")
    public void iShouldSeeItUpdatedToday() {
        String stringDate = response.jsonPath().getString("data.updateLabel.label.updatedAt");
        stringDate = stringDate.substring(0, stringDate.length() - 1);
        LocalDate date = LocalDateTime.parse(stringDate).toLocalDate();
        assertThat(date.until(LocalDate.now(), ChronoUnit.DAYS), is(0L));
    }

    @And("I should see the url has been updated")
    public void iShouldSeeTheUrlHasBeenUpdated() {
        assertThat(response.jsonPath().getString("data.updateLabel.label.url"), is("https://github.com/ahmedghamad/Graphql-Label-Testing/labels/wontfix%202"));
    }
}
