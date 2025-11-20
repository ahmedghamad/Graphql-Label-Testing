package utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class TestBase {

    //Read a .graphql file from resources.graphql

    protected static final String Base_URI = Config.getGitHubBaseUri();

    protected static final String TOKEN = Config.getToken();

    protected static final String OWNER = Config.getOwner();

    protected static final String REPO = Config.getRepo();

    public static String readQuery(String filename) throws IOException {
        return Files.readString(Paths.get("src/test/resources/graphql/" + filename));
    }

    public static Response executeQuery(String query, String operationName, Map<String, Object> variables){
        Map<String, Object> body = Map.of(
                "query", query,
                "operationName", operationName,
                "variables", variables
        );

        return RestAssured
                .given()
                .baseUri(Base_URI)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .body(body)
                .log().all()
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();
    }


}