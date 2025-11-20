package steps;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class CreateStepdefs extends utils.TestBase {

    //private static String repositoryId;
    private static final String repoid="R_kgDOQY0lAw"; ;
    private static Response response;
    private static String labelId;

    private static String labelName;
    private static String labelColor;
    private static String labelDescription;

    @Given("I have a valid GitHub repository ID")
    public void iHaveAValidGitHubRepositoryID() throws IOException {
        System.out.println("Using repository ID: " + repoid);

    }

    @Given("I have a unique label name {string} with color {string} and description {string}")
    public void iHaveAUniqueLabelNameWithColorAndDescription(String name, String color, String description) {
        labelName = name + "_" + java.util.UUID.randomUUID();
        labelColor = color;
        labelDescription = description;
    }

    @When("I send a createLabel mutation to the GitHub GraphQL API")
    public void iSendACreateLabelMutationToTheGitHubGraphQLAPI() throws IOException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("repoId", repoid);
        variables.put("name", labelName);
        variables.put("color", labelColor);
        variables.put("description", labelDescription);

        response = executeQuery(readQuery("CreateLabel.graphql"), "CreateLabel", variables);
        labelId = response.jsonPath().getString("data.createLabel.label.id");
        System.out.println("Created Label ID: " + labelId);
    }

    @Then("the API should respond with status code {int}")
    public void theAPIShouldRespondWithStatusCode(int statusCode) {
        assertThat(response.statusCode(), is(statusCode));


    }

    @And("label should have correct details")
    public void labelShouldHaveCorrectDetails() {
        String actualName = response.jsonPath().getString("data.createLabel.label.name");
        String actualColor = response.jsonPath().getString("data.createLabel.label.color");
        String actualDescription = response.jsonPath().getString("data.createLabel.label.description");

        assertThat(labelId, notNullValue());
        assertThat(actualName, is(labelName));
        assertThat(actualColor, is(labelColor));
        assertThat(actualDescription, is(labelDescription));

    }

    public static String getLabelId() {
        return labelId;
    }
}


