package com.example.calculatorautotesting;

import com.example.calculatorautotesting.calculations.MathCommand;
import com.example.calculatorautotesting.calculations.MathFactory;
import com.example.calculatorautotesting.calculations.ToDouble;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class CalculatorAutotestingApplication {

    public static void main(String[] args) {

        SpringApplication.run(CalculatorAutotestingApplication.class, args);
        while (true) {
            String[] params = readParams();
            Double p1 = ToDouble.toDouble(params[1]);
            Double p2 = ToDouble.toDouble(params[2]);
            MathFactory factory = new MathFactory();
            MathCommand mc = factory.calculate(params);
            System.out.println(mc.calculate(p1, p2));
        }
    }
    private static String[] readParams() {
        String[] str = new String[3];
        Scanner in = new Scanner(System.in);
        int i = 0;
        while (i < str.length) {
            str[i] = in.nextLine();
            i++;
        }
        return str;
    }
}
