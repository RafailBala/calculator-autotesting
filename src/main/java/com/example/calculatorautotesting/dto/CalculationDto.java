package com.example.calculatorautotesting.dto;

import com.example.calculatorautotesting.model.Calculation;
import com.google.gson.GsonBuilder;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class CalculationDto {
    private Long id;
    private String number_1;
    private int notation_1;
    private String number_2;
    private int notation_2;
    private String operation;
    private String date;
    private String time;
    private String result;
    public CalculationDto() {
    }
    public CalculationDto(Long id, String number_1, int notation_1, String number_2, int notation_2, String operation, String date, String time, String result) {
        this.id = id;
        this.number_1 = number_1;
        this.notation_1 = notation_1;
        this.number_2 = number_2;
        this.notation_2 = notation_2;
        this.operation = operation;
        this.date = date;
        this.time = time;
        this.result = result;
    }

    public Calculation toCalculation(){
        Calculation calculation = new Calculation();
        calculation.setId(id);
        calculation.setNumber_1(number_1);
        calculation.setNotation_1(notation_1);
        calculation.setNumber_2(number_2);
        calculation.setNotation_2(notation_2);
        calculation.setOperation(operation);
        calculation.setDate(date);
        calculation.setTime(time);
        calculation.setResult(result);
        return calculation;
    }
    public CalculationDto fromCalculation(Calculation calculation) {
        CalculationDto calculationDto = new CalculationDto();
        calculationDto.setId(calculation.getId());
        calculationDto.setNumber_1(calculation.getNumber_1());
        calculationDto.setNumber_2(calculation.getNumber_2());
        calculationDto.setNotation_1(calculation.getNotation_1());
        calculationDto.setNotation_2(calculation.getNotation_2());
        calculationDto.setOperation(calculation.getOperation());
        calculationDto.setDate(calculation.getDate());
        calculationDto.setTime(calculation.getTime());
        calculationDto.setResult(calculation.getResult());
        return calculationDto;
    }
    @Override
    public String toString() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(this);
    }
}
