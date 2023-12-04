package com.example.calculatorautotesting;

import com.example.calculatorautotesting.exception.CalculationException;
import com.example.calculatorautotesting.calculations.MathCommand;
import com.example.calculatorautotesting.calculations.MathFactory;
import com.example.calculatorautotesting.calculations.ToDouble;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.ParameterizedTest;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

public class ParamTest {
    static Stream<Arguments> stringsAllCommandProvider() {
        return Stream.of(
                Arguments.of((Object) new String[]{"1","-", "0b1111","0b1110"}),
                Arguments.of((Object) new String[]{"30","+", "0b1111","0b1111"}),
                Arguments.of((Object) new String[]{"144","*", "0b1100","0b1100"}),
                Arguments.of((Object) new String[]{"6","/", "0b1100","0b0010"}),

                Arguments.of((Object) new String[]{"-1","-", "010","011"}),
                Arguments.of((Object) new String[]{"31","+", "020","017"}),
                Arguments.of((Object) new String[]{"100","*", "012","012"}),
                Arguments.of((Object) new String[]{"6","/", "014","02"}),

                Arguments.of((Object) new String[]{"6", "*", "2", "3"}),
                Arguments.of((Object) new String[]{"12","-", "24","12"}),
                Arguments.of((Object) new String[]{"5", "/", "25", "5"}),
                Arguments.of((Object) new String[]{"12","-", "24","12"}),

                Arguments.of((Object) new String[]{"-1","-", "0xA","0xB"}),
                Arguments.of((Object) new String[]{"27","+", "0xD","0xE"}),
                Arguments.of((Object) new String[]{"25","*", "0x5","0x5"}),
                Arguments.of((Object) new String[]{"4","/", "0x10","0x4"})
        );
    }
    @ParameterizedTest
    @MethodSource("stringsAllCommandProvider")
    void testSourceAllCommands(String[] args) {
        String[] strings = {args[1], args[2], args[3]};
        Double p1 = ToDouble.toDouble(strings[1]);
        Double p2 = ToDouble.toDouble(strings[2]);
        MathFactory factory = new MathFactory();
        MathCommand mc = factory.calculate(strings);
        assertEquals(Double.parseDouble(args[0]), mc.calculate(p1, p2));
    }
    @ParameterizedTest
    @CsvSource({"6,/,0b1100,0b0010"})
    void testCsvSource(String arg1,String arg2,String arg3,String arg4) {
        String[] strings = {arg2, arg3, arg4};
        Double p1 = ToDouble.toDouble(strings[1]);
        Double p2 = ToDouble.toDouble(strings[2]);
        MathFactory factory = new MathFactory();
        MathCommand mc = factory.calculate(strings);
        assertEquals(Double.parseDouble(arg1), mc.calculate(p1, p2));
    }


    static Stream<Arguments> stringsExceptionProvider() {
        return Stream.of(
                Arguments.of((Object) new String[]{"/", "25", "0"}),
                Arguments.of((Object) new String[]{"/", "0b1100","0b0"}),
                Arguments.of((Object) new String[]{"/", "014","00"}),
                Arguments.of((Object) new String[]{"/", "0x10","0x0"})
        );
    }
    @ParameterizedTest
    @MethodSource("stringsExceptionProvider")
    public void assertThrowsDivByZero(String [] args) {
        String[] strings = {args[0], args[1], args[2]};
        Exception exception = assertThrows(CalculationException.class, ()->  {
            Double p1 = ToDouble.toDouble(strings[1]);
            Double p2 = ToDouble.toDouble(strings[2]);
            MathFactory factory = new MathFactory();
            MathCommand mc = factory.calculate(strings);
            mc.calculate(p1, p2);
        });
        String expectedMessage = "Ошибка вычисления! Деление на 0";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}