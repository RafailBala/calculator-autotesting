package com.example.calculatorautotesting.api.models;

import com.google.gson.GsonBuilder;
import lombok.Data;

@Data
public class RequestModel {
    private String method;
    private String body;
    private String path;
    private String url;

    public RequestModel(String method, String body, String path, String url) {
        this.method = method;
        this.body = body;
        this.path = path;
        this.url = url;
    }
    @Override
    public String toString() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(this);
    }
}
