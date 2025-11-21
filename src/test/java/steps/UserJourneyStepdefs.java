package steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import pojos.update.UpdateResponse;
import utils.TestBase;

import java.io.IOException;
import java.util.Map;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserJourneyStepdefs extends TestBase {

    public String Label_ID;

    @And("I should see the name {string}")
    public void iShouldSeeTheName(String arg0) {
        Response createResponse  = ReadLabelStepdefs.getCreateResponse();
        for (int i=0; i<createResponse.jsonPath().getList("data.repository.labels.nodes").size();i++) {
            if (createResponse.jsonPath().getString("data.repository.labels.nodes["+i+"].id").equals(Label_ID)) {
                assertThat(createResponse.jsonPath().getList("data.repository.labels.nodes["+i+"]"), contains(arg0));
            }
        }
    }



    @And("I update the label name to {string}")
    public void iUpdateTheLabelNameTo(String arg0) throws IOException {
        String newTitle = "Updated Title";
        String newBody = "Updated Body";
        String newColour = "ffffff";
        Response response;
        response = TestBase.executeQuery(
                TestBase.readQuery("UpdateLabel.graphql"),
                "UpdateLabel",
                Map.of(
                        "id", Label_ID,
                        "name", newTitle,
                        "description", newBody,
                        "color", newColour
                )
        );
    }


    @And("I get the created label ID")
    public void iGetTheCreatedLabelID() {
        Label_ID = CreateStepdefs.getLabelId();
    }
}
