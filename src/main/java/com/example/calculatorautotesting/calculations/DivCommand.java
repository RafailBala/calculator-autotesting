package com.example.calculatorautotesting.calculations;

import com.example.calculatorautotesting.exception.CalculationException;

public class DivCommand implements com.example.calculatorautotesting.calculations.MathCommand {
    @Override
    public double calculate(double x, double y) {
        double result=x/y;
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            throw new CalculationException("Превышен порог значений");
        }
        return result;
    }
}
