package rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rest.pojos.BulkPostcodeResponse;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BulkPostcodeLookup {

    private static Response response;

    private static final String BASE_URI = utils.Config.getBasePostcodeUri();

    private static BulkPostcodeResponse pojoResponse;

    @BeforeAll
    static void beforeAll(){
        Map<String, Object> body = Map.of(
                "postcodes", List.of("PR3 0SG", "M45 6GN", "EX165BL")
        );
        response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath("/postcodes")
                .header("Accept", "application/json")
                .contentType("application/json")
                .body(body)
                .when()
                .log().all()
                .post()
                .thenReturn();
        pojoResponse = response.as(BulkPostcodeResponse.class);
    }
    @Test
    @DisplayName("Status code 200 returned")
    void testStatusCode200(){
        assertThat(response.statusCode(), is(200));
    }

    @Test
    @DisplayName("Correct postcode returned in response")
    void testCorrectPostcodeInResponse(){
        var result = response.jsonPath().getList("result");
        assertThat(result.size(),is(3));

        assertThat(response.jsonPath().getString("result[0].query"), is("PR3 0SG"));
        assertThat(response.jsonPath().getString("result[1].query"), is("M45 6GN"));
        assertThat(response.jsonPath().getString("result[2].query"), is("EX165BL"));

    }
    @Test
    @DisplayName("Correct postcodes returned in response with Pojo")
    public void testAllPostcodesWithPojo(){
        assertThat(pojoResponse.getStatus(),is(200));
    }
    @Test
    @DisplayName("Correct postcode returned in response with Pojo")
    public void testCorrectPostcodeResponseWithPojo(){
        assertThat(pojoResponse.getStatus(),is(200));
        assertThat(pojoResponse.getResult().size(),is(3));
        assertThat(pojoResponse.getResult().get(0).getQuery(),is("PR3 0SG"));
        assertThat(pojoResponse.getResult().get(1).getQuery(), is("M45 6GN"));
        assertThat(pojoResponse.getResult().get(2).getQuery(), is("EX165BL"));
    }


}
