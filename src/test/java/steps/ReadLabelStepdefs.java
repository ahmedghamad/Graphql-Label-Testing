package steps;


import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import utils.TestBase;
import io.restassured.response.Response;
import org.junit.Assert;
import utils.Config;

import java.io.IOException;
import java.util.Map;

import static utils.TestBase.executeQuery;
import static utils.TestBase.readQuery;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReadLabelStepdefs{

    private String labelDescribtion;
    private String color;
    private String labelId;
    private String labelName;
    private static Response createResponse;
    private String token;


    @Given("I have a valid token")
    public void valid_token_read_label() {
            token = Config.getToken();
        }


    @Then("I want to be able to get issues with labels")
    public void iWantToBeAbleToGetIssuesWithLabels() throws IOException {
        createResponse = executeQuery(
                readQuery("Readlabel.graphql"),
                "Repository",
                Map.of(
                        "name", "Graphql-Label-Testing",
                        "owner", "ahmedghamad"
                )
        );

        labelName = createResponse.jsonPath().getString("data.repository.labels.nodes[1].name");
        labelId = createResponse.jsonPath().getString("data.repository.labels.nodes[1].id");
        labelDescribtion = createResponse.jsonPath().getString("data.repository.labels.nodes[1].description");
        color = createResponse.jsonPath().getString("data.repository.labels.nodes[1].color");


        System.out.println("Label Name: " + labelName);
        System.out.println("Label ID: " + labelId);
        System.out.println("Label Description: " + labelDescribtion);
        System.out.println("Color: " + color);

    }


}
