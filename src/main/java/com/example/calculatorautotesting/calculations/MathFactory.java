package com.example.calculatorautotesting.calculations;

import com.example.calculatorautotesting.exception.CalculationException;

public class MathFactory {
    public  MathCommand calculate(String [] params) {
        String operator = params[0];
        double value1;
        double value2;
        if(params[0].trim().length() == 0 || params[1].trim().length() == 0 ||params[2].trim().length() == 0 ) throw new CalculationException("Пустая строка");
        try {
            Double d1;
            Double d2;
            d1= com.example.calculatorautotesting.calculations.ToDouble.toDouble(params[1]);
            d2= com.example.calculatorautotesting.calculations.ToDouble.toDouble(params[2]);
            value1=d1;
            value2=d2;
        } catch (NumberFormatException e) {
            throw new CalculationException("Введено не число");
        }
        if (operator.equals("/") && value2==0) throw new CalculationException("Деление на 0");
        if(value1> Integer.MAX_VALUE || value1 < Integer.MIN_VALUE||value2 > Integer.MAX_VALUE || value2 < Integer.MIN_VALUE) throw new CalculationException("Превышен порог значений");

        switch (params[0]) {
            case "+":
                return new AddCommand();
            case "-":
                return new com.example.calculatorautotesting.calculations.SubCommand();
            case "*":
                return new com.example.calculatorautotesting.calculations.MultCommand();
            case "/":
                return new DivCommand();
        }
        throw new CalculationException("Неизвестный оператор");
    }
}
