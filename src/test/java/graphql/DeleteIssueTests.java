package graphql;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

public class DeleteIssueTests extends TestBase {

    private static String createdIssueId;
    private static final String repositoryId = "R_kgDOQXbbnA";
    private static Response createResponse;
    private static Response deleteResponse;
    private static String originalTitle;
    private static String originalBody;

    @BeforeAll
    static void beforeAll() throws IOException {

        createResponse = executeQuery(
                readQuery("AddNewIssue.graphql"),
                "AddNewIssue",
                Map.of(
                        "repositoryId", repositoryId,
                        "title", "Issue created for delete test",
                        "body", "This issue will be deleted during the test"
                )
        );


        createdIssueId = createResponse.jsonPath().getString("data.createIssue.issue.id");
        originalTitle = createResponse.jsonPath().getString("data.createIssue.issue.title");
        originalBody = createResponse.jsonPath().getString("data.createIssue.issue.body");

        deleteResponse= executeQuery(
                readQuery("DeleteIssue.graphql"),
                "DeleteIssue",
                Map.of("issue", createdIssueId)
        );


        createdIssueId = null;
    }


    @AfterAll
    static void afterAll() throws IOException {


        if (createdIssueId == null) {
            executeQuery(
                    readQuery("AddNewIssue.graphql"),
                    "AddNewIssue",
                    Map.of(
                            "repositoryId", repositoryId,
                            "title", originalTitle + " (Restored)",
                            "body", originalBody  + " (Restored)"
                    )
            );
        }
    }
    @Test
    @DisplayName("Delete issue returns HTTP 200")
    void deleteIssueReturnsHttp200() throws IOException {
        assertThat(deleteResponse.statusCode(), is(200));

    }

    @Test
    @DisplayName("Has correct content type header")
    void hasCorrectContentTypeHeader() {
        assertThat(createResponse.contentType(), containsString("application/json"));
    }

    @Test
    @DisplayName("No GraphQL errors in response")
    void noGraphQLErrors() {
        assertThat(createResponse.jsonPath().getList("errors"), anyOf(nullValue(), empty()));

    }



    @Test
    @DisplayName("Created issue has correct title and body")
    void createdIssueHasCorrectTitleAndBody() {
        assertThat(createResponse.jsonPath().getString("data.createIssue.issue.title"), is(originalTitle));
        assertThat(createResponse.jsonPath().getString("data.createIssue.issue.body"), is(originalBody));
    }

    @Test
    @DisplayName("Created issue has valid URL and ID")
    void createdIssueHasValidUrlAndId() {
        assertThat(createResponse.jsonPath().getString("data.createIssue.issue.id"), not(emptyOrNullString()));
        assertThat(createResponse.jsonPath().getString("data.createIssue.issue.number"), notNullValue());
        assertThat(createResponse.jsonPath().getString("data.createIssue.issue.url"), containsString("https://github.com/"));
    }

    @Test
    @DisplayName("Response body contains all expected fields")
    void responseBodyContainsAllExpectedFields() {
        createResponse.then()
                .body("data.createIssue.issue.id", notNullValue())
                .body("data.createIssue.issue.number", notNullValue())
                .body("data.createIssue.issue.url", notNullValue())
                .body("data.createIssue.issue.title", notNullValue());
    }



}
