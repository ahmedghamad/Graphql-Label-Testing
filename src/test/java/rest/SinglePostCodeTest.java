package rest;

import rest.pojos.SinglePostcodeResponse;
import utils.Config;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class SinglePostCodeTest {

    private static Response response;

    private static final String BASE_URI = utils.Config.getBasePostcodeUri();
    private static SinglePostcodeResponse pojoResponse;
    @BeforeAll
    static void beforeAll(){
        response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath("/postcodes")
                .header("Accept", "text/json")
                .when()
                .log().all()
                .get("/EC2Y5AS")
                .thenReturn();
        pojoResponse = response.as(SinglePostcodeResponse.class);

    }

    @Test
    @DisplayName("Status code 200 returned")
    void testStatusCode200(){
        assertThat(response.statusCode(), is(200));
        assertThat(pojoResponse.getStatus(),is(200));
    }

    @Test
    @DisplayName("The server name in the headers is cloudflare")
    void testServerName(){
        assertThat(response.header("Server"), is("cloudflare"));
    }


    @Test
    @DisplayName("Correct postcode returned in response")
    void testCorrectPostcodeInResponse(){
        assertThat(response.jsonPath().getString("result.postcode"), is("EC2Y 5AS"));
        assertThat(pojoResponse.getResult().getPostcode(),is("EC2Y5AS"));
    }

    @Test
    @DisplayName("Correct name of the Primary Care Trust")
    void testPrimaryCareTrustName(){
        assertThat(response.jsonPath().getString("result.primary_care_trust"), is("City and Hackney Teaching"));
    }
    @Test
    @DisplayName("Correct total number is 14")
    void testTotalNumberOfCodes() {
        Map<String, Object> codes = response.jsonPath().getMap("result.codes");

        assertThat(codes.size(), is(14));
    }


//    @Test
//    @DisplayName("Status code 200 returned Alt")
//    void testStatusCode200_alt(){
//        RestAssured
//                .get("https://api.postcodes.io/postcodes/EC2Y5AS")
//                .then()
//                .assertThat()
//                .statusCode(200);
//    }
}
