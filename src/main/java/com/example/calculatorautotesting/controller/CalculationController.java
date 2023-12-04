package com.example.calculatorautotesting.controller;

import com.example.calculatorautotesting.dto.CalculationDto;
import com.example.calculatorautotesting.dto.SearchDto;
import com.example.calculatorautotesting.service.CalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calculations/")
public class CalculationController {

    private final CalculationService calculationService;

    @PostMapping(value = "operation")
    public ResponseEntity<?> calc(@RequestBody CalculationDto calculationDto) throws IOException {
        try {
            CalculationDto calculation = calculationService.calculation(calculationDto);
            if (calculation != null)
            // return new ResponseEntity<>(calculation, HttpStatus.OK);
                return new ResponseEntity<>(calculation.getResult(), HttpStatus.OK);
            else return new ResponseEntity<>("Нет данных!",HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "findByDateBetween")
    public ResponseEntity<?> findByDateBetween(@RequestBody SearchDto searchDto) throws IOException {
        try {
            List<CalculationDto> result = calculationService.findByDateBetween(searchDto);
            if (result != null)
                return new ResponseEntity<>(result, HttpStatus.OK);
            else return new ResponseEntity<>("Нет данных!",HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "findByTimeBetween")
    public ResponseEntity<?> findByTimeBetween(@RequestBody SearchDto searchDto) throws IOException {
        try {
            List<CalculationDto> result = calculationService.findByTimeBetween(searchDto);
            if (result != null)
                return new ResponseEntity<>(result, HttpStatus.OK);
            else return new ResponseEntity<>("Нет данных!",HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "all")
    public ResponseEntity<?> getAll() throws IOException {
        try {
            return new ResponseEntity<>(calculationService.getAll(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
