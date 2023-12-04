package com.example.calculatorautotesting.service;

import com.example.calculatorautotesting.calculations.ControlNotation;
import com.example.calculatorautotesting.calculations.MathCommand;
import com.example.calculatorautotesting.calculations.MathFactory;
import com.example.calculatorautotesting.calculations.ToDouble;
import com.example.calculatorautotesting.dto.CalculationDto;
import com.example.calculatorautotesting.dto.SearchDto;
import com.example.calculatorautotesting.model.Calculation;
import com.example.calculatorautotesting.repo.CalculationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional//(readOnly= true)
public class CalculationService {

    private final CalculationRepository calculationRepository;
    @Autowired
    private CalculationDto calculationDto;

    public List<CalculationDto> getAll(){
        List<Calculation> calculations =calculationRepository.findAll();
        return calculations
                .stream()
                .map(calculationDto::fromCalculation)
                .toList();
    }
    public CalculationDto calculation(CalculationDto calculationDto) {
        ControlNotation controlNotation = new ControlNotation();

        String num1 = calculationDto.getNumber_1();
        String num2 = calculationDto.getNumber_2();

        if (controlNotation.controlNotation(num1, calculationDto.getNotation_1()) && controlNotation.controlNotation(num2, calculationDto.getNotation_2())) {

            String[] params = {calculationDto.getOperation(), calculationDto.getNumber_1(), calculationDto.getNumber_2()};
            Double p1 = ToDouble.toDouble(params[1]);
            Double p2 = ToDouble.toDouble(params[2]);
            MathFactory factory = new MathFactory();
            MathCommand mc = factory.calculate(params);
            String result =String.valueOf(mc.calculate(p1, p2));
            calculationDto.setDate(String.valueOf(LocalDate.now()));
            calculationDto.setTime(String.valueOf(LocalTime.now()));
            calculationDto.setResult(result);
            Calculation calculation=calculationDto.toCalculation();
            calculationRepository.save(calculation);
            return calculationDto;
        }
        else return null;
    }

    public List<CalculationDto> findByDateBetween(SearchDto searchDto){
        List<Calculation> calculations=calculationRepository.findByDateBetween(searchDto.getNumber_1(),searchDto.getNumber_2());
        if(calculations.size()!=0) {
            return calculations
                    .stream()
                    .map(calculationDto::fromCalculation)
                    .toList();
        }
        return null;
    }
    public List<CalculationDto> findByTimeBetween(SearchDto searchDto){
        List<Calculation> calculations=calculationRepository.findByTimeBetween(searchDto.getNumber_1(), searchDto.getNumber_2());
        if(calculations.size()!=0) {
            return calculations
                    .stream()
                    .map(calculationDto::fromCalculation)
                    .toList();
        }
        return null;
    }
}
