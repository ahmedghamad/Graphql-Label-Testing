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
    private static String repoid = "R_kgDOQY0lAw";
    ;
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


    @And("no errors message")
    public void noErrorsMessage() {
        assertThat(response.jsonPath().getList("errors"), is(nullValue()));
    }


    @And("I set an invalid repository ID")
    public void iSetAnInvalidRepositoryID() {
        repoid = "1232bhj";


    }

    @And("the API should return an invalid repository error")
    public void theAPIShouldReturnAnInvalidRepositoryError() {
        String errorMsg = response.jsonPath().getString("errors[0].message");
        assertThat(errorMsg, containsString("Could not resolve"));
    }


    @And("validate error type is Not_Found")
    public void validateErrorTypeIsNot_Found() {
        String errType = response.jsonPath().getString("errors[0].type");
        assertThat(errType, containsString("NOT_FOUND"));

    }

    @When("I send a createLabel with no description")
    public void iSendACreateLabelWithNoDescription() throws IOException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("repoId", repoid);
        variables.put("name", labelName);
        variables.put("color", labelColor);
        response = executeQuery(readQuery("CreateLabel.graphql"), "CreateLabel", variables);
        labelId = response.jsonPath().getString("data.createLabel.label.id");


    }


    @And("check the labels")
    public void checkTheLabels() {
        String actualName = response.jsonPath().getString("data.createLabel.label.name");
        String actualColor = response.jsonPath().getString("data.createLabel.label.color");
        String actualDescription = response.jsonPath().getString("data.createLabel.label.description");

        assertThat(labelId, notNullValue());
        assertThat(actualName, is(labelName));
        assertThat(actualColor, is(labelColor));

    }

    @When("I send a createLabel mutation with out the color field to the GitHub GraphQL API")
    public void iSendACreateLabelMutationWithOutTheColorFieldToTheGitHubGraphQLAPI() throws IOException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("repoId", repoid);
        variables.put("name", labelName);
        variables.put("description", labelDescription);

        response = executeQuery(readQuery("CreateLabel.graphql"), "CreateLabel", variables);
        labelId = response.jsonPath().getString("data.createLabel.label.id");
        System.out.println("Created Label ID: " + labelId);


    }

    @And("I get Variable $color of type String! was provided invalid value")
    public void iGetVariable$colorOfTypeStringWasProvidedInvalidValue() {
        String errMsg = response.jsonPath().getString("errors[0].message");
        assertThat(errMsg, is("Variable $color of type String! was provided invalid value"));


    }


    public static String getLabelId() {
        return labelId;
    }

    @Given("I set a valid repository ID")
    public void iSetAValidRepositoryID() {
        repoid = "R_kgDOQY0lAw";


    }

}
