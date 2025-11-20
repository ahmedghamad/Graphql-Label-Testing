package com.sparta.runners;


import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/Registration.feature",
        glue = "com.sparta.steps",
        plugin = {"pretty"}
)

public class RegistrationRunner {
}
