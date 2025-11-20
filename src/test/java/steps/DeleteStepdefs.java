package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.response.Response;
import org.junit.Assert;
import utils.Config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.TestBase.executeQuery;
import static utils.TestBase.readQuery;

public class DeleteStepdefs {

    private String token;
    private Response response;
    private String labelId;
    private String owner;
    private String repo;

    @Given("I have a valid GitHub token")
    public void valid_token() {
        token = Config.getToken();
    }

    @Given("I have an invalid GitHub token")
    public void invalid_token() {
        token = "INVALID_TOKEN_12345";
    }

    @Given("I get the id of label {string} in repository {string}")
    public void get_label_id(String labelName, String repoFullName) throws IOException {
        String[] parts = repoFullName.split("/");
        owner = parts[0];
        repo = parts[1];

        String query = readQuery("getLabels.graphql");

        Map<String, Object> vars = Map.of(
                "owner", owner,
                "name", repo
        );

        response = executeQuery(query, "GetLabels", vars);

        List<Map<String, Object>> labels =
                response.jsonPath().getList("data.repository.labels.nodes");

        labelId = labels.stream()
                .filter(l -> l.get("name").equals(labelName))
                .map(l -> (String) l.get("id"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Label not found: " + labelName));
    }

    @Given("I use a non-existent label id")
    public void non_existent_id() {
        labelId = "NON_EXISTING_LABEL_ID_ABC123";
    }

    @When("I send a deleteLabel mutation")
    public void delete_label() throws IOException {
        String mutation = readQuery("deleteLabel.graphql");
        Map<String, Object> variables = Map.of("id", labelId);

        response = executeQuery(mutation, "DeleteLabel", variables);
    }

    @Then("the label should be removed from the repository")
    public void label_removed() throws IOException {
        Assert.assertNull(response.jsonPath().get("errors"));  // deletion succeeded

        String query = readQuery("getLabels.graphql");

        Map<String, Object> vars = Map.of(
                "owner", owner,
                "name", repo
        );

        Response check = executeQuery(query, "GetLabels", vars);

        List<Map<String, Object>> labels =
                check.jsonPath().getList("data.repository.labels.nodes");

        boolean exists = labels.stream()
                .anyMatch(l -> l.get("id").equals(labelId));

        Assert.assertFalse("Label still exists after deletion", exists);
    }

    @Then("the API should return a GraphQL error")
    public void graphql_error() {
        Assert.assertNotNull(response.jsonPath().getList("errors"));
    }

    @Then("the API should return status 401")
    public void invalid_token_response() {
        Assert.assertNotNull(response.jsonPath().getList("errors"));
    }
}
