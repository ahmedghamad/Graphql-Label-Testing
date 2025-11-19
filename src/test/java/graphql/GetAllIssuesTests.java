package graphql;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class GetAllIssuesTests extends TestBase {

    private static Response response;

    @BeforeAll
    static void setUp() throws IOException {
        String query = readQuery("GetAllOpenIssues.graphql");

        Map<String, Object> variables = Map.of(
                "owner", OWNER,
                "repo", REPO
        );

        response = executeQuery(query,"GetAllOpenIssues", variables);
    }
    @Test
    @DisplayName("Get All Issues - status code is 200")
    void testStatusCode(){
        assertThat(response.statusCode(),is(200));
    }

}
