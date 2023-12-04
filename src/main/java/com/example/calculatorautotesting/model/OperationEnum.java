package com.example.calculatorautotesting.model;

public enum OperationEnum {
    ADD("+"), DIV("/"),MULT("*"), SUB("-");
    private final String code;
    OperationEnum(String code) {
        this.code=code;
    }
    public String getCode() {
        return code;
    }
}
