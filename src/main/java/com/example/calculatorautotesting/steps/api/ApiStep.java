package com.example.calculatorautotesting.steps.api;

import com.example.calculatorautotesting.api.ApiRequest;
import com.example.calculatorautotesting.api.models.RequestModel;
import com.example.calculatorautotesting.dto.CalculationDto;
import com.example.calculatorautotesting.repo.CalculationRepository;
import com.example.calculatorautotesting.utils.CompareUtil;
import com.example.calculatorautotesting.utils.ContextHolder;
import com.example.calculatorautotesting.utils.VariableUtil;
import com.google.gson.Gson;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import lombok.Data;
import net.minidev.json.JSONArray;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.calculatorautotesting.utils.ContextHolder.replaceVarsIfPresent;
import static com.example.calculatorautotesting.utils.JsonUtil.getFieldFromJson;
import static org.junit.Assert.assertEquals;

@Data
public class ApiStep {
    private static final Logger LOG = LoggerFactory.getLogger(ApiStep.class);
    private ApiRequest apiRequest;

    @Value("${spring.datasource.url}")
    private String localServerPort;

    private final CalculationRepository calculationRepository;
    private CalculationDto [] responseB;
    @И("создать запрос")
    public void createRequest(RequestModel requestModel) {
        //jdbc:postgresql://localhost:5432/calculation-db

        //String port= localServerPort.substring(15,33);
        //String fullUrl="http"+port+requestModel.getUrl();
        //String fullUrl="http://localhost:8089"+requestModel.getUrl();//порт заданный
        //requestModel.setUrl(fullUrl);

        apiRequest = new ApiRequest(requestModel);
    }

    @И("добавить header")
    public void addHeaders(DataTable dataTable) {
        Map<String, String> headers = new HashMap<>();
        dataTable.asLists().forEach(it -> headers.put(it.get(0), replaceVarsIfPresent(it.get(1))));
        apiRequest.setHeaders(headers);
    }

    @И("добавить query параметры")
    public void addQuery(DataTable dataTable) {
        Map<String, String> query = new HashMap<>();
        dataTable.asLists().forEach(it -> query.put(it.get(0), replaceVarsIfPresent(it.get(1))));
        apiRequest.setQuery(query);
    }

    @И("отправить запрос")
    public void send() {
        apiRequest.sendRequest();
    }

    @И("статус код {int}")
    public void expectStatusCode(int code) {
        int actualStatusCode = apiRequest.getResponse().statusCode();
        assertEquals(actualStatusCode, code);
    }

    @И("извлечь данные")
    public void extractVariables(Map<String, String> vars) {
        String responseBody = apiRequest.getResponse().body().asPrettyString();
        vars.forEach((k, jsonPath) -> {
            jsonPath = replaceVarsIfPresent(jsonPath);
            String extractedValue = VariableUtil.extractBrackets(getFieldFromJson(responseBody, jsonPath));
            ContextHolder.put(k, extractedValue);
            LOG.info("Извлечены данные: {}={}", k, extractedValue);
        });
    }

    @И("извлечь все данные")
    public void extractAllVariables() {
        responseB = apiRequest.getResponse().as(CalculationDto[].class);;
    }


    @И("сравнить значения")
    public void compareVars(DataTable table) {
        table.asLists().forEach(it -> {
            String expect = replaceVarsIfPresent(it.get(0));
            String actual = replaceVarsIfPresent(it.get(2));
            boolean compareResult = CompareUtil.compare(expect, actual, it.get(1));
            Assert.assertTrue(String.format("Ожидаемое: '%s'\nФактическое: '%s'\nОператор сравнения: '%s'\n", expect, actual, it.get(1)), compareResult);
            LOG.info("Сравнение значений: {} {} {}", expect, it.get(1), actual);
        });
    }
    @И("сравнить все значения из файла {string}")
    public void compareAllVars(String path) throws FileNotFoundException, ParseException {
        Gson gson = new Gson();
        Object o = new JSONParser().parse(new FileReader(path));

        JSONArray jsonArray = (JSONArray) o;
        CalculationDto[] myArray = gson.fromJson(String.valueOf(jsonArray), CalculationDto[].class);
        CalculationDto[] calculationDtos = responseB;
        if (calculationDtos != null) {
            for (int i = 0; i < calculationDtos.length; i++) {
                assertEquals(calculationDtos[i], myArray[i]);
            }
        }
    }
    @After
    public void after(){
        calculationRepository.deleteAll();
    }
    @Дано("в БД имеются следующие данные")
    public void вБДИмеютсяСледующиеДанные(DataTable table) {

        CalculationDto calculationDto;
        List<List<String>> data = table.asLists(String.class);
        for(int i=0;i< data.size();i++){
            calculationDto=new CalculationDto(Long.parseLong(data.get(i).get(0)),data.get(i).get(1),
                                              Integer.parseInt(data.get(i).get(2)),data.get(i).get(3),
                                              Integer.parseInt(data.get(i).get(4)),data.get(i).get(5),
                                              data.get(i).get(6),data.get(i).get(7), data.get(i).get(8));
            calculationRepository.save(calculationDto.toCalculation());
        }
    }

    @Дано("даты {string} и {string}")
    public void датыИ(String arg1, String arg2) throws java.text.ParseException {
          //просто для примера
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        Date date = inputFormat.parse(arg1);
        String outputText = outputFormat.format(date);
        ContextHolder.put("number_1", outputText);
        //LOGGER.info("Сгенерирована переменная: {}={}", k, value);

        date = inputFormat.parse(arg2);
        outputText = outputFormat.format(date);
        ContextHolder.put("number_2", outputText);
        //LOGGER.info("Сгенерирована переменная: {}={}", k, value);
    }
}
