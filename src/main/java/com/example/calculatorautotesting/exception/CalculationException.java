package com.example.calculatorautotesting.exception;

public class CalculationException extends RuntimeException {

    private static final String MESSAGE = "Ошибка вычисления!";
    public CalculationException() {
        super(MESSAGE);
    }
    public CalculationException(String str) {
        super(MESSAGE + " " + str);
    }
    public CalculationException(Throwable e) {
        super(MESSAGE, e);
    }
    public CalculationException(String str, Throwable e) {
        super(MESSAGE + " " + str, e);
    }
}
