package steps;


import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
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
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.nullValue;
import static utils.TestBase.executeQuery;
import static utils.TestBase.readQuery;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReadLabelStepdefs{

    private String labelDescribtion;
    private String color;
    private String labelCreatedAt;
    private String labelId;
    private String labelName;
    private String labelUrl;
    private static Response createResponse;
    private static final String name = "Graphql-Label-Testing";
    private static final String owner = "ahmedghamad";
    private String token;


    @Given("I have a valid token")
    public void valid_token_read_label() {
            token = Config.getToken();
        }


    @When("I view the labels")
    public void iWantToBeAbleToGetIssuesWithLabels() throws IOException {
        createResponse = executeQuery(
                readQuery("Readlabel.graphql"),
                "Repository",
                Map.of(
                        "name", name,
                        "owner", owner
                )
        );

        labelName = createResponse.jsonPath().getString("data.repository.labels.nodes[1].name");
        labelId = createResponse.jsonPath().getString("data.repository.labels.nodes[1].id");
        labelDescribtion = createResponse.jsonPath().getString("data.repository.labels.nodes[1].description");
        color = createResponse.jsonPath().getString("data.repository.labels.nodes[1].color");
        labelCreatedAt = createResponse.jsonPath().getString("data.repository.labels.nodes[1].createdAt");
        labelUrl = createResponse.jsonPath().getString("data.repository.labels.nodes[1].url");


        System.out.println("Label Name: " + labelName);
        System.out.println("Label ID: " + labelId);
        System.out.println("Label Description: " + labelDescribtion);
        System.out.println("Color: " + color);

    }


    @Then("I should a status code of {string}")
    public void iShouldAStatusCodeOf(String arg0) {
        assertThat(createResponse.statusCode(), is(200));
    }


    @And("I should see  no error messages")
    public void iShouldSeeNoErrorMessages() {
        assertThat(createResponse.jsonPath().getList("errors"), is(nullValue()));
    }


    @And("I should see the  color")
    public void iShouldSeeTheColor() {
        assertThat(createResponse.jsonPath().getString("data.repository.labels.nodes[1].color"),is(color));
    }

    @And("I should see the  createdAt")
    public void iShouldSeeTheCreatedAt() {
        assertThat(createResponse.jsonPath().getString("data.repository.labels.nodes[1].createdAt"),is(labelCreatedAt));
    }

    @And("I should see the description")
    public void iShouldSeeTheDescription() {
        assertThat(createResponse.jsonPath().getString("data.repository.labels.nodes[1].description"),is(labelDescribtion));
    }

    @And("I should see the id")
    public void iShouldSeeTheId() {
        assertThat(createResponse.jsonPath().getString("data.repository.labels.nodes[1].id"),is(labelId));
    }

    @And("I should see the name")
    public void iShouldSeeTheName() {
        assertThat(createResponse.jsonPath().getString("data.repository.labels.nodes[1].name"), is(labelName));
    }
    @And("I should see the url")
    public void iShouldSeeTheUrl() {
        assertThat(createResponse.jsonPath().getString("data.repository.labels.nodes[1].url"),is(labelUrl));
    }


    @When("I UnSuccessfully the labels")
    public void iUnSuccessfullyTheLabels() throws IOException {
        createResponse = executeQuery(
                readQuery("Readlabel.graphql"),
                "Repository",
                Map.of(
                        "name", name,
                        "owner", " "

                )
        );
    }

    @And("I should see  an error messages")
    public void iShouldSeeAnErrorMessages() {
        String errorMessage = createResponse.jsonPath().getString("errors[0].message");
        assertThat(errorMessage, is("Could not resolve to a Repository with the name ' /Graphql-Label-Testing'."));
    }
}
