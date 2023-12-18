package com.example.calculatorautotesting;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
//@ExtendWith(SpringExtension.class)
@CucumberContextConfiguration
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = "spring.flyway.clean-disabled=false")
@CucumberOptions(plugin = {"pretty"},snippets = CucumberOptions.SnippetType.UNDERSCORE, features = "src/test/resources/features/api")
public class RunnerBDD extends AuditVizualizationBaseTest {

}