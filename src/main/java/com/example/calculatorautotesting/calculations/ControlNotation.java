package com.example.calculatorautotesting.calculations;

import com.example.calculatorautotesting.exception.CalculationException;

public class ControlNotation {
    public  Boolean controlNotation(String param, int param2){
        try {
            if(param.startsWith("0b") || param.startsWith("0B")){
                if(param2 == 2)
                    return true;
                else return false;
            }
            else  if(param.startsWith("0x")|| param.startsWith("0X")){
                if(param2 == 16)
                    return true;
                else return false;
            }
            else  if(param.startsWith("0")&&param.length()!=1){
                if(param2 == 8)
                    return true;
                else return false;
            }
            else {
                if(param2 == 10)
                    return true;
                else return false;
            }
        } catch (NumberFormatException e) {
            throw new CalculationException("Неправильный формат числа");
        }
    }
}
