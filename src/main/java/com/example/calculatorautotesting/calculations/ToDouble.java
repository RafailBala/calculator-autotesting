package com.example.calculatorautotesting.calculations;

import com.example.calculatorautotesting.exception.CalculationException;

public class ToDouble {
    public static Double toDouble(String param){
        try {
            Double p;
            if(param.startsWith("0b") || param.startsWith("0B")){
                p= (double) Integer.parseInt(param.substring(2), 2);
            }
            else  if(param.startsWith("0x")|| param.startsWith("0X")){
                p = (double) Integer.parseInt(param.substring(2), 16);
            }
            else  if(param.startsWith("0")&&param.length()!=1){
                p = (double) Integer.parseInt(param.substring(1), 8);
            }
            else {
                p = Double.parseDouble(param);
            }
            return p;
        } catch (NumberFormatException e) {
            throw new CalculationException("Неправильный формат числа");
        }
    }
}
