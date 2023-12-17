package com.example.calculatorautotesting.api;

import com.example.calculatorautotesting.api.listeners.RestAssuredCustomLogger;
import com.example.calculatorautotesting.api.models.RequestModel;
import com.example.calculatorautotesting.api.properties.RestConfigurations;
import com.example.calculatorautotesting.utils.FileUtil;
import com.example.calculatorautotesting.utils.RegexUtil;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;

import java.net.URI;
import java.util.Map;

import static com.example.calculatorautotesting.utils.ContextHolder.replaceVarsIfPresent;
import static io.restassured.RestAssured.given;


public class ApiRequest {
    private final static RestConfigurations CONFIGURATIONS = ConfigFactory.create(RestConfigurations.class,
            System.getProperties(),
            System.getenv());

    private String baseUrl;
    private String path;
    private Method method;
    private String body;
    private String fullUrl;
    private Response response;

    private RequestSpecBuilder builder;

    public ApiRequest(RequestModel requestModel) {
        this.builder = new RequestSpecBuilder();

        this.baseUrl = CONFIGURATIONS.getBaseUrl();
        this.path = replaceVarsIfPresent(requestModel.getPath());
        this.method = Method.valueOf(requestModel.getMethod());
        this.body = requestModel.getBody();
        this.fullUrl = replaceVarsIfPresent(requestModel.getUrl());

        URI uri;

        if (!fullUrl.isEmpty()) {
            uri = URI.create(fullUrl.replace(" ", "+"));
        } else {
            uri = URI.create(baseUrl);
            builder.setBasePath(path);
        }

        this.builder.setBaseUri(uri);
        setBodyFromFile();
        addLoggingListener();
    }

    public Response getResponse() {
        return response;
    }

    /**
     * Сеттит заголовки
     */
    public void setHeaders(Map<String, String> headers) {
        headers.forEach((k, v) -> {
            builder.addHeader(k, v);
        });
    }

    /**
     * Сеттит query-параметры
     */
    public void setQuery(Map<String, String> query) {
        query.forEach((k, v) -> {
            builder.addQueryParam(k, v);
        });
    }

    /**
     * Отправляет сформированный запрос
     */
    public void sendRequest() {
        RequestSpecification requestSpecification = builder.build();

        // attachRequestResponseToAllure(response, body);
        this.response = given()
                .spec(requestSpecification)
                .request(method);
    }

    /**
     * Сессит тело запроса из файла
     */
    private void setBodyFromFile() {
        if (body != null && RegexUtil.getMatch(body, ".*\\.json")) {
            body = replaceVarsIfPresent(FileUtil.readBodyFromJsonDir(body));
            builder.setBody(body);
        }
    }

    /**
     * Аттачит тело запроса и тело ответа в шаг отправки запроса
     */
   // private void attachRequestResponseToAllure(Response response, String requestBody) {
   //     if (requestBody != null) {
   //         Allure.addAttachment(
   //                 "Request",
   //                 "application/json",
   //                 new ByteArrayInputStream(requestBody.getBytes(StandardCharsets.UTF_8)),
   //                 ".txt");
   //     }
   //     String responseBody = JsonUtil.jsonToUtf(response.body().asPrettyString());
   //     Allure.addAttachment("Response", "application/json", responseBody, ".txt");
   // }

    /**
     * Добавляет логгер, печатающий в консоль данные запросов и ответов
     */
    private void addLoggingListener() {
        builder.addFilter(new RestAssuredCustomLogger());
    }
}
