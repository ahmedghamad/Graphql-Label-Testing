package graphql;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class UpdateIssueTests extends TestBase {
    private static final String ISSUE_ID = "I_kwDOQXbbnM7Yk7uc";
    public static Response response;
    private static String originalTitle;
    private static String originalBody;
    private static String newTitle;
    private static String newBody;

    @BeforeAll
    static void beforeAll() throws IOException{

        //1. Get the original value
        Response readResponse = executeQuery(
                readQuery("GetIssueById.graphql"),
                "GetIssueById",
                Map.of("id", ISSUE_ID)
        );

        originalTitle = readResponse.jsonPath().getString("data.node.title");
        originalBody = readResponse.jsonPath().getString("data.node.body");

        //2.  Create my new values
        newTitle = originalTitle + " my first update";
        newBody = originalBody + " and everything";

        // 3. update the issues BEFORE the test
        response = executeQuery(
                readQuery("UpdateIssue.graphql"),
                "UpdateIssue",
                Map.of(
                        "issueId", ISSUE_ID,
                        "title", newTitle,
                        "body", newBody
                )
        );
    }

    @AfterAll
    static void afterAll() throws IOException {

        String mutation = readQuery("UpdateIssue.graphql");

        Map<String, Object> variables = Map.of(
                "issueId", ISSUE_ID,
                "title", originalTitle,
                "body", originalBody
        );

        executeQuery(mutation, "UpdateIssue", variables);
    }
    @Test
    @DisplayName("Updated issue contains the new title")
    void updatedIssueContainsNewTitle(){
        String returnedTitle = response.jsonPath().getString("data.updateIssue.issue.title");
        assertThat(returnedTitle, is(newTitle));
    }

    @Test
    @DisplayName("Returns HTTP 200")
    void returnsHttp200() {
        assertThat(response.statusCode(), is(200));
    }

    @Test
    @DisplayName("Response contains no GraphQL errors")
    void responseContainsNoGraphQlErrors() {
        List<Map<String, Object>> errors = response.jsonPath().getList("errors");
        assertThat(errors, anyOf(nullValue(), hasSize(0)));
    }


    @Test
    @DisplayName("Updated issue contains the new body")
    void updatedIssueContainsNewBody() {
        String returnedBody = response.jsonPath().getString("data.updateIssue.issue.body");
        assertThat(returnedBody, is(newBody));
    }
}
