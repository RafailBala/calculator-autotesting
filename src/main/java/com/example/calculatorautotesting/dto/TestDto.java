package com.example.calculatorautotesting.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TestDto {
    @SerializedName("number_1")
    private String number_1;
    @SerializedName("notation_1")
    private int notation_1;
    @SerializedName("number_2")
    private String number_2;
    @SerializedName("notation_2")
    private int notation_2;
    @SerializedName("operation")
    private String operation;

    public TestDto() {
    }
    public TestDto(String number_1, int notation_1, String number_2, int notation_2, String operation) {
        this.number_1 = number_1;
        this.notation_1 = notation_1;
        this.number_2 = number_2;
        this.notation_2 = notation_2;
        this.operation = operation;
    }
}
