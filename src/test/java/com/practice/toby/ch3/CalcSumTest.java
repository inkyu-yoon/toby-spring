package com.practice.toby.ch3;

import com.practice.toby.ch3.calc.Calculator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CalcSumTest {
    Calculator calculator;
    String numFilePath;

    @BeforeEach
    public void setUp() {
        this.calculator = new Calculator();
        this.numFilePath = "C:\\Users\\ikyoo\\Desktop\\Spring\\toby\\numbers.txt";
    }

    @Test
    public void sumOfNumbers() throws IOException {
        Assertions.assertThat(calculator.calcSum(numFilePath)).isEqualTo(15);
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        Assertions.assertThat(calculator.calcMultiply(numFilePath)).isEqualTo(120);
    }

    @Test
    public void concatStr() {
        Assertions.assertThat(calculator.concatTexts(numFilePath)).isEqualTo("12345");
    }
}
