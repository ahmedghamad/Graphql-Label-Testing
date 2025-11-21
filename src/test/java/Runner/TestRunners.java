package Runner;

//package Runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "steps",
        plugin = {"pretty",
                "json:target/cucumber.json",
                "junit:target/cucumber.xml"
        }
)
public class TestRunners {
}
