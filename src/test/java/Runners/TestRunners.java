package Runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/CreateLabel.feature",
        glue = "steps",
        plugin = {"pretty"}
)
public class TestRunners {
}
