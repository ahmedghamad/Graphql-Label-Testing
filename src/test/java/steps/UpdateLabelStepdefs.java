package steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import pojos.update.UpdateResponse;
import utils.TestBase;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateLabelStepdefs extends TestBase {


    private static String Label_ID;

    private static String newTitle;
    private static String newBody;
    private static String newColour;

    private static Response response;
    public static UpdateResponse pojoResponse;


    @When("I update the label")
    public void iUpdateTheLabel() throws IOException {
        newTitle = "Updated Title";
        newBody = "Updated Body";
        newColour = "ffffff";

        response = TestBase.executeQuery(
                TestBase.readQuery("UpdateLabel.graphql"),
                "UpdateLabel",
                new HashMap<>(Map.of(
                        "id", Label_ID,
                        "name", newTitle,
                        "description", newBody,
                        "color", newColour
                ))
        );
        pojoResponse = response.as(UpdateResponse.class);
    }

    @Then("I should see the updated title")
    public void iShouldSeeTheUpdatedTitle() {
        //assertThat(response.jsonPath().getString("data.updateLabel.label.name"), is(newTitle));
        assertThat(pojoResponse.getData().getUpdateLabel().getLabel().getName(), is(newTitle));
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
        //assertThat(response.jsonPath().getString("data.updateLabel.label.description"), is(newBody));
        assertThat(pojoResponse.getData().getUpdateLabel().getLabel().getDescription(), is(newBody));
    }


    @And("I should see it has been updated today")
    public void iShouldSeeItUpdatedToday() {
        String stringDate = pojoResponse.getData().getUpdateLabel().getLabel().getUpdatedAt();
        stringDate = stringDate.substring(0, stringDate.length() - 1);
        LocalDate date = LocalDateTime.parse(stringDate).toLocalDate();
        assertThat(date.until(LocalDate.now(), ChronoUnit.DAYS), is(0L));
    }

    @And("I should see the url has been updated")
    public void iShouldSeeTheUrlHasBeenUpdated() {
        assertThat(pojoResponse.getData().getUpdateLabel().getLabel().getUrl(), is("https://github.com/ahmedghamad/Graphql-Label-Testing/labels/Updated%20Title"));
    }

    @And("I get the label ID")
    public void iGetTheLabelID() {
        Label_ID = CreateStepdefs.getLabelId();
    }


    @And("I delete the label")
    public void delete_label() throws IOException {
        String mutation = readQuery("deleteLabel.graphql");
        Map<String, Object> variables = Map.of("id", Label_ID);

        response = executeQuery(mutation, "DeleteLabel", variables);
    }

    @When("I update the label without the name")
    public void iUpdateTheLabelWithoutTheName() throws IOException {
        newTitle = "Updated Title";
        newBody = "Updated Body";
        newColour = "ffffff";

        response = TestBase.executeQuery(
                TestBase.readQuery("UpdateLabel.graphql"),
                "UpdateLabel",
                Map.of(
                        "id", Label_ID,
                        "description", newBody,
                        "color", newColour
                )
        );
    }

    @And("I should see a invalid value for name error message")
    public void iShouldSeeAErrorMessage() {
        List<Map<String, Object>> errors = response.jsonPath().getList("errors");
        String message = (String) errors.get(0).get("message");
        assertThat(message, is("Variable $name of type String! was provided invalid value"));
    }

    @When("I update the label without the ID")
    public void iUpdateTheLabelWithoutTheID() throws IOException {
            newTitle = "Updated Title";
            newBody = "Updated Body";
            newColour = "ffffff";

            response = TestBase.executeQuery(
                    TestBase.readQuery("UpdateLabel.graphql"),
                    "UpdateLabel",
                    Map.of(
                            "name", newTitle,
                            "color", newColour,
                            "description", newBody
                    )
            );
    }

    @And("I should see a invalid value for ID error message")
    public void iShouldSeeAInvalidValueErrorMessage() {
        List<Map<String, Object>> errors = response.jsonPath().getList("errors");
        String message = (String) errors.get(0).get("message");
        assertThat(message, is("Variable $id of type ID! was provided invalid value"));
    }


}
