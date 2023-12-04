package com.example.calculatorautotesting;

import com.example.calculatorautotesting.calculations.MathCommand;
import com.example.calculatorautotesting.calculations.MathFactory;
import com.example.calculatorautotesting.calculations.ToDouble;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class DynamTest {
    @TestFactory
    public Stream<DynamicTest> testFactory() {
        InputStream inputFS = getClass().getClassLoader().getResourceAsStream("testData.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
        return br.lines().map(mapCsvLineToDynamicTest());
    }
    private Function<String, DynamicTest> mapCsvLineToDynamicTest() {
        return s -> {
            final String[] values = s.split(",");
            return dynamicTest(values[0], () -> assertAddition(values));
        };
    }
    private void assertAddition(String[] args) {
        String[] strings = {args[1], args[2], args[3]};
        Double p1 = ToDouble.toDouble(strings[1]);
        Double p2 = ToDouble.toDouble(strings[2]);
        MathFactory factory = new MathFactory();
        MathCommand mc = factory.calculate(strings);
        assertEquals(Double.parseDouble(args[0]), mc.calculate(p1, p2));
    }
}
