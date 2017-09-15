package dog.broker;

/**
 * Created by morric67 on 13/09/2017.
 */

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/integration/resources/dog/broker/features",
        glue = "dog.broker.features",
        tags = {"~@wip"},
        format = {"pretty", "html:build/test-results/integration/cucumber-html-report",
                "json:build/test-results/integration/cucumber-json-report.json"})
public class FeatureTests {
}
