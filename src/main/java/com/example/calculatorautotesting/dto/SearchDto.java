package com.example.calculatorautotesting.dto;

import com.example.calculatorautotesting.model.Calculation;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class SearchDto {
   // private Long id;
    @SerializedName("number_1")
    private String number_1;
    @SerializedName("number_2")
    private String number_2;

    public SearchDto() {
    }
    public SearchDto(String number_1, String number_2) {
        this.number_1 = number_1;
        this.number_2 = number_2;
    }

    public Calculation toCalculation(){
        Calculation calculation = new Calculation();
        calculation.setNumber_1(number_1);
        calculation.setNumber_2(number_2);

        return calculation;
    }
    public SearchDto fromCalculation(Calculation calculation) {
        SearchDto calculationDto = new SearchDto("","");
        calculationDto.setNumber_1(calculation.getNumber_1());
        calculationDto.setNumber_2(calculation.getNumber_2());
        return calculationDto;
    }
}
