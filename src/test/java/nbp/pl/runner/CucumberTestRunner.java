package nbp.pl.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"nbp.pl.steps"},
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true
)

public class CucumberTestRunner {
}
