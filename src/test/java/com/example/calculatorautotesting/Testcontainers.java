package com.example.calculatorautotesting;

import com.example.calculatorautotesting.dto.CalculationDto;
import com.example.calculatorautotesting.dto.SearchDto;
import com.example.calculatorautotesting.dto.TestDto;
import com.google.gson.Gson;
import net.minidev.json.JSONArray;
import net.minidev.json.parser.JSONParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.io.FileReader;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@FlywayTest(locationsForMigrate = "db/data/V20231025_2__Insert.sql")
public class Testcontainers extends AuditVizualizationBaseTest {

    @Test
    @Sql(scripts = "/db/data/V20231025_2__Insert.sql")
    @DisplayName("Тест Get все")
    public void allTest() {
        try {
            //из repo в json строку
            //ObjectMapper mapper = new ObjectMapper();
            //String jsonString = mapper.writeValueAsString(calculationRepository.findAll());
            //System.out.println(jsonString)

            Gson gson = new Gson();
            Object o = new JSONParser().parse(new FileReader("C:\\Users\\balae\\IdeaProjects\\calculator-autotesting\\src\\main\\resources\\db\\data\\allData.json"));

            JSONArray jsonArray = (JSONArray) o;
            CalculationDto[] myArray = gson.fromJson(String.valueOf(jsonArray), CalculationDto[].class);
            CalculationDto[] calculationDtos = getAll();

            if (calculationDtos != null) {
                for (int i = 0; i < calculationDtos.length; i++) {
                    assertEquals(calculationDtos[i], myArray[i]);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public CalculationDto[] getAll() {
        try {
            return given()
                    .when()
                    .get("/api/calculations/all")
                    .then()
                    .assertThat().statusCode(200)
                    .extract().response().as(CalculationDto[].class);
            //.body(equalTo(calculationRepository.findAll()));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Test
    //@FlywayTest(locationsForMigrate = "db/data")
    @DisplayName("Тест Get между")
    public void findByDateBetweenTest() throws IOException, InterruptedException {
        try {
            //из repo в json строку
            //ObjectMapper mapper = new ObjectMapper();
            //String jsonString = mapper.writeValueAsString(calculationRepository.findByDateBetween("2023-11-12","2023-11-20"));
            //System.out.println(jsonString);
            Gson gson = new Gson();
            Object o = new JSONParser().parse(new FileReader("C:\\Users\\balae\\IdeaProjects\\calculator-autotesting\\src\\main\\resources\\db\\data\\betweenDate.json"));

            JSONArray jsonArray = (JSONArray) o;
            CalculationDto[] myArray = gson.fromJson(String.valueOf(jsonArray), CalculationDto[].class);
            CalculationDto[] calculationDtos = getFindByDateBetween();

            if (calculationDtos != null) {
                for (int i = 0; i < calculationDtos.length; i++) {
                    assertEquals(calculationDtos[i], myArray[i]);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public CalculationDto[] getFindByDateBetween() throws IOException, InterruptedException {
        try {
            SearchDto searchDto = new SearchDto("2023-11-12", "2023-11-20");
            return given()
                    .when()
                    .body(searchDto)
                    .get("/api/calculations/findByDateBetween")
                    .then()
                    .assertThat().statusCode(200)
                    .extract().response().as(CalculationDto[].class);
            //.equals(jsonArray);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Test
    @DisplayName("Тест Get расчет")
    public void operationTest() {
        try {
            TestDto testDto = new TestDto("11", 10, "11", 10, "+");
            operation(testDto, "22.0");
            testDto = new TestDto("0x9", 16, "5", 10, "+");
            operation(testDto, "14.0");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void operation(TestDto testDto, String result) {
        try {
            //while (true) ;
            given()
                    .body(testDto)
                    .when()
                    .post("/api/calculations/operation")//
                    .then()
                    .statusCode(200)
                    .body(equalTo(result));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
