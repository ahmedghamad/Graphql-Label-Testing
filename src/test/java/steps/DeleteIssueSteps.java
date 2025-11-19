package steps;

import graphql.TestBase;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DeleteIssueSteps extends TestBase {

    private static String createdIssueId;
    private static Response createResponse;
    private static Response deleteResponse;

    private static String originalTitle;
    private static String originalBody;

    @Given("an issue exists for deletion")
    public void createIssueForDeletion() throws IOException {
        createResponse = executeQuery(
                readQuery("AddNewIssue.graphql"),
                "AddNewIssue",
                Map.of(
                        "repositoryId", "R_kgDOQXbbnA",
                        "title", "Issue created for delete test",
                        "body", "This issue will be deleted during the test"
                )
        );

        createdIssueId = createResponse.jsonPath().getString("data.createIssue.issue.id");
        originalTitle = createResponse.jsonPath().getString("data.createIssue.issue.title");
        originalBody  = createResponse.jsonPath().getString("data.createIssue.issue.body");
    }

    @When("I delete the issue")
    public void deleteIssue() throws IOException {

        deleteResponse = executeQuery(
                readQuery("DeleteIssue.graphql"),
                "DeleteIssue",
                Map.of("issue", createdIssueId)
        );
    }

    @Then("the delete response status should be {int}")
    public void verifyStatus(int expectedStatus) {
        assertThat(deleteResponse.statusCode(), is(expectedStatus));
    }

    @Then("the original issue should be restored")
    public void restoreIssue() throws IOException {
        executeQuery(
                readQuery("AddNewIssue.graphql"),
                "AddNewIssue",
                Map.of(
                        "repositoryId", "R_kgDOQXbbnA",
                        "title", originalTitle + " (Restored)",
                        "body", originalBody + " (Restored)"
                )
        );
    }
}
