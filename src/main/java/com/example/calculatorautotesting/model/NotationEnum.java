package com.example.calculatorautotesting.model;

public enum NotationEnum {
    BINARY(2), OCTAL(8),DECIMAL(10), HEX(16);
    private  final int code;
    NotationEnum(int code) {
        this.code=code;
    }
    public  int getCode() {
        return code;
    }
}
