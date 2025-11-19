package graphql;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class GetIssuesByidTests extends TestBase{

    private static Response getAllIssuesResponse;
    private static Response specificIssueResponse;

    private static String issueId;


    @BeforeAll
    static void setUp() throws IOException {
        String queryAll = readQuery("GetAllOpenIssues.graphql");



        Map<String, Object> variablesAll = Map.of(
                "owner", OWNER,
                "repo", REPO
        );

        getAllIssuesResponse = executeQuery(queryAll,"GetAllOpenIssues", variablesAll);

        issueId = getAllIssuesResponse.jsonPath().getString("data.repository.issues.nodes[0].id");



        String queryById = readQuery("GetIssueById.graphql");

        Map<String, Object> variablesId = Map.of(
                "id", issueId
        );

        specificIssueResponse = executeQuery(queryById, "GetIssueById", variablesId);
        System.out.println(issueId);
    }

    @Test
    @DisplayName("Status code is 200 ")
    void checkStatusCodeAndNoErrors(){
        assertThat(specificIssueResponse.getStatusCode(),is(200));
    }

    @Test
    @DisplayName("No errors")
    void testErrors(){
        assertThat(specificIssueResponse.jsonPath().getList("errors"), is(nullValue()));
    }

    @Test
    @DisplayName("Get All Issues — First issue has correct title")
    void testFirstIssueTitle() {
        String firstIssueTitle =specificIssueResponse.jsonPath().getString("data.node.title");
        assertThat("First issue title should be 'My first issue'",
                firstIssueTitle, is("My first issue"));
    }


}
