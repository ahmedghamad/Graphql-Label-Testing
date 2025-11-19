package graphql;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class TestBase {

    protected static final String BASE_URI = Config.getGitHubBaseUri();

    protected static final String TOKEN = Config.getToken();

    protected static final String OWNER = Config.getOwner();

    protected static final String REPO = Config.getRepo();

    protected static String readQuery(String filename) throws IOException {
        return Files.readString(Paths.get("src/test/resources/graphql/" + filename));
    }

    protected static Response executeQuery(String query, String opertionName, Map<String, Object> variables){
        Map<String,Object> body= Map.of(
                "query", query,
                "operationName", opertionName,
                "variables",variables
        );
        return RestAssured
                .given()
                    .baseUri(BASE_URI)
                    .header("Authorization", "Bearer "+TOKEN)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .log().all()
                .when()
                    .post()
                .then()
                    .log().all()
                    .extract().response();
    }
}
