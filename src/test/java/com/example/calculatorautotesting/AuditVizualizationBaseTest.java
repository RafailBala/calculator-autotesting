package com.example.calculatorautotesting;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.*;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = "spring.flyway.clean-disabled=false")
@ContextConfiguration//(initializers = {AuditVizualizationBaseTest.Initializer.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class, SqlScriptsTestExecutionListener.class})
public abstract class AuditVizualizationBaseTest {
    static final PostgreSQLContainer POSTGRE_SQL_CONTAINER;
    @LocalServerPort
    private Integer port;

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:12")
                .withDatabaseName("calculation-db")
                .withUsername("postgres")
                .withPassword("rafail")
                .withInitScript("db/migration/V20231025_1__Create_tables.sql");
        POSTGRE_SQL_CONTAINER.start();
        //RestAssured.baseURI = "http://localhost:" + POSTGRE_SQL_CONTAINER.getJdbcUrl().substring(28,33)+"/";
    }
  //static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
  //    //@DynamicPropertySource
  //     public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
  //        TestPropertyValues.of(
  //                "spring.datasource.url=" + POSTGRE_SQL_CONTAINER.getJdbcUrl(),
  //                "spring.datasource.username=" + POSTGRE_SQL_CONTAINER.getUsername(),
  //                "spring.datasource.password=" + POSTGRE_SQL_CONTAINER.getPassword(),
  //                "spring.flyway.enabled=true"
  //        ).applyTo(configurableApplicationContext.getEnvironment());
  //    }
  //}
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
        registry.add("spring.flyway.url",POSTGRE_SQL_CONTAINER::getJdbcUrl);
    }
    @BeforeEach
    public  void prepare() throws IOException {
        try {
            //System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
            //String baseUri = System.getProperty("base.uri");
            //if (baseUri == null || baseUri.isEmpty()) {
            //    throw new RuntimeException("В файле \"config.properties\" отсутствует значение \"base.uri\"");
            //}
            RestAssured.requestSpecification = new RequestSpecBuilder()
                    .setBaseUri("http://localhost:"+port)
                    //.addHeader("Authorization", "token " + login().getToken())
                    .setAccept(ContentType.JSON)
                    .setContentType(ContentType.JSON)
                    .log(LogDetail.ALL)
                    .build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}