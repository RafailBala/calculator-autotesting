package com.example.calculatorautotesting;
//
//@CucumberOptions(
//        plugin = {
//                "pretty",
//                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
//        },
//        features = "classpath:features",
//        glue = {"ru.lanit.at.steps", "ru.lanit.at.hooks", "ru.lanit.at.corecommonstep"}
//)
//public class Runner extends AbstractTestNGCucumberTests {
//
//   //@Override
//   //@DataProvider(parallel = true)
//
//   //public Object[][] scenarios() {
//   //    return super.scenarios();
//   //}
//}

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;


@RunWith(Cucumber.class)
//@ExtendWith(SpringExtension.class)
@CucumberContextConfiguration
@SpringBootTest
@CucumberOptions(plugin = {"pretty"},snippets = CucumberOptions.SnippetType.UNDERSCORE, features = "src/test/resources/features/api")
public class RunnerBDD extends AuditVizualizationBaseTest {
}