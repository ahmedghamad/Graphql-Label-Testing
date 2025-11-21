package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import utils.Config;
import utils.TestBase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteStepdefs {

    private String token;
    private Response response;
    private String labelId;
    private String labelName;

    private static final String repoId = "R_kgDOQY0lAw";

    @Given("I have a valid GitHub token")
    public void validToken() {
        token = Config.getToken();
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("GitHub token not set!");
        }
    }

    @Given("I have an invalid GitHub token")
    public void invalidToken() {
        token = "INVALID_TOKEN_12345";
    }

    @Given("I create a new label to delete")
    public void createLabelToDelete() throws IOException {
        if (token.startsWith("INVALID")) {
            labelId = "LA_NONEXISTING";
            System.out.println("Skipping label creation for invalid token test");
            return;
        }

        labelName = "TempLabel_" + java.util.UUID.randomUUID();

        Map<String, Object> variables = new HashMap<>();
        variables.put("repoId", repoId);
        variables.put("name", labelName);
        variables.put("color", "FF0000");
        variables.put("description", "Temporary label for deletion");

        response = TestBase.executeQuery(TestBase.readQuery("CreateLabel.graphql"), "CreateLabel", variables);
        labelId = response.jsonPath().getString("data.createLabel.label.id");

        if (labelId == null) {
            throw new RuntimeException("Failed to create label: " + response.asPrettyString());
        }

        System.out.println("Created label: " + labelName + " (ID: " + labelId + ")");
    }

    @Given("I have a non-existent label id")
    public void nonExistentLabelId() {
        labelId = "NON_EXISTING_LABEL_ID_ABC123";
    }

    @When("I send a deleteLabel mutation")
    public void deleteLabel() {
        String mutation = """
            mutation DeleteLabel($id: ID!) {
              deleteLabel(input: {id: $id}) {
                clientMutationId
              }
            }
        """;

        Map<String, Object> variables = Map.of("id", labelId);

        // Execute the mutation using the current token (valid or invalid)
        response = TestBase.executeQuery(mutation, "DeleteLabel", variables);
        System.out.println("Delete response:\n" + response.asPrettyString());
    }

    @Then("the label should be removed from the repository")
    public void labelRemoved() {
        List<Map<String, Object>> errors = response.jsonPath().getList("errors");
        Assert.assertNull("Deletion failed: " + response.asPrettyString(), errors);
        System.out.println("Label deleted successfully!");
    }

    @Then("the API should return an error message")
    public void graphqlError() {
        List<Map<String, Object>> errors = response.jsonPath().getList("errors");
        Assert.assertNotNull("Expected GraphQL error", errors);
        System.out.println("Error message: " + errors.get(0).get("message"));
    }

    @Then("the API should reject the request")
    public void invalidTokenResponse() {
        List<Map<String, Object>> errors = response.jsonPath().getList("errors");
        Assert.assertNotNull("Expected errors for invalid token", errors);
        Assert.assertFalse("Expected at least one error", errors.isEmpty());

        Object deleteResult = response.jsonPath().get("data.deleteLabel");
        Assert.assertNull("Delete mutation should return null with invalid token", deleteResult);

        System.out.println("Invalid token response: " + errors.get(0).get("message"));
    }

}
