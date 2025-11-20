package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import utils.Config;
import utils.TestBase;

import java.util.List;
import java.util.Map;

public class DeleteStepdefs {

    private String token;
    private Response response;
    private String labelId;
    private String owner;
    private String repo;

    @Given("I have a valid GitHub token")
    public void valid_token() {
        token = Config.getToken();
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("GitHub token not set in Config or environment variables");
        }
    }

    @Given("I have an invalid GitHub token")
    public void invalid_token() {
        token = "INVALID_TOKEN_12345";
    }

    @Given("I get the id of label {string} in repository {string}")
    public void get_label_id(String labelName, String repoFullName) {
        String[] parts = repoFullName.split("/");
        owner = parts[0];
        repo = parts[1];

        String query = """
            query GetLabels($owner: String!, $name: String!) {
              repository(owner: $owner, name: $name) {
                labels(first: 100) {
                  nodes {
                    id
                    name
                  }
                }
              }
            }
        """;

        Map<String, Object> variables = Map.of(
                "owner", owner,
                "name", repo
        );

        // execute the query using TestBase
        response = TestBase.executeQuery(query, "GetLabels", variables);

        // debug output
        System.out.println("GitHub response for labels:\n" + response.asPrettyString());

        List<Map<String, Object>> labels = response.jsonPath()
                .getList("data.repository.labels.nodes");

        if (labels == null) {
            throw new RuntimeException("No labels found or repository invalid. Response: " + response.asPrettyString());
        }

        labelId = labels.stream()
                .filter(l -> labelName.equals(l.get("name")))
                .map(l -> (String) l.get("id"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Label not found: " + labelName));
    }

    @Given("I have a non-existent label id")
    public void non_existent_id() {
        labelId = "NON_EXISTING_LABEL_ID_ABC123";
    }

    @Given("I have a valid label id for deletion in the repository {string}")
    public void valid_label_id_for_invalid_token(String repoFullName) {
        String[] parts = repoFullName.split("/");
        String owner = parts[0];
        String repo = parts[1];

        String query = """
        query GetLabels($owner: String!, $name: String!) {
          repository(owner: $owner, name: $name) {
            labels(first: 100) {
              nodes {
                id
                name
              }
            }
          }
        }
    """;

        Map<String, Object> variables = Map.of(
                "owner", owner,
                "name", repo
        );

        Response tempResponse = TestBase.executeQuery(query, "GetLabels", variables, Config.getToken());

        List<Map<String, Object>> labels = tempResponse.jsonPath().getList("data.repository.labels.nodes");

        if (labels == null || labels.isEmpty()) {
            throw new RuntimeException("No labels found in repository: " + repoFullName);
        }

        labelId = (String) labels.get(0).get("id");

        System.out.println("Fetched valid label ID: " + labelId);
    }


    @When("I send a deleteLabel mutation")
    public void delete_label() {
        String mutation = """
            mutation DeleteLabel($id: ID!) {
              deleteLabel(input: {id: $id}) {
                clientMutationId
              }
            }
        """;

        Map<String, Object> variables = Map.of("id", labelId);
        response = TestBase.executeQuery(mutation, "DeleteLabel", variables);

        System.out.println("GitHub response for deletion:\n" + response.asPrettyString());
    }

    @Then("the label should be removed from the repository")
    public void label_removed() {
        Assert.assertNull("Deletion failed with errors: " + response.asPrettyString(),
                response.jsonPath().get("errors"));

        // verify deletion
        String query = """
            query GetLabels($owner: String!, $name: String!) {
              repository(owner: $owner, name: $name) {
                labels(first: 100) {
                  nodes {
                    id
                    name
                  }
                }
              }
            }
        """;

        Map<String, Object> vars = Map.of("owner", owner, "name", repo);
        Response check = TestBase.executeQuery(query, "GetLabels", vars);

        List<Map<String, Object>> labels = check.jsonPath().getList("data.repository.labels.nodes");

        boolean exists = labels != null && labels.stream()
                .anyMatch(l -> l.get("id").equals(labelId));

        Assert.assertFalse("Label still exists after deletion", exists);
    }

    @Then("the API should return an error message")
    public void graphql_error() {
        List<Map<String, Object>> errors = response.jsonPath().getList("errors");
        Assert.assertNotNull("Expected GraphQL errors but got none", errors);
        Assert.assertTrue("Expected at least one GraphQL error", errors.size() > 0);

        System.out.println("GraphQL Error: " + errors.get(0).get("message"));
    }

    @Then("the API should reject the request")
    public void invalid_token_response() {
        List<Map<String, Object>> errors = response.jsonPath().getList("errors");

        Assert.assertNotNull("Expected errors in the response for invalid token, but got none", errors);
        Assert.assertFalse("Expected at least one error for invalid token, but got none", errors.isEmpty());

        boolean hasAuthError = errors.stream()
                .anyMatch(err -> ((String) err.get("message")).toLowerCase().contains("authentication") ||
                        ((String) err.get("message")).toLowerCase().contains("token"));

        Assert.assertTrue("Expected authentication error message in response, but none found", hasAuthError);

        System.out.println("Response for invalid token: " + response.asString());
    }



}
