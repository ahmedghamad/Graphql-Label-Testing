package rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rest.pojos.BulkPostcodeResponse;
import rest.pojos.SinglePostcodeResponse;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SingleOutcodeLookup {

    private static Response response;

    private static final String BASE_URI = utils.Config.getBasePostcodeUri();
    private static SinglePostcodeResponse pojoResponse;

    @BeforeAll
    static void beforeAll(){
        response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath("/outcodes")
                .header("Accept", "application/json")
                .when()
                .log().all()
                .get("/SW1A")
                .thenReturn();
        pojoResponse = response.as(SinglePostcodeResponse.class);
    }

    @Test
    @DisplayName("Status code 200 returned")
    void testStatusCode200(){
        assertThat(response.statusCode(), is(200));
        assertThat(pojoResponse.getStatus(),is(200));
    }


}
