package com.spring.calc.shared.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculatorRequestTest {

    private CalculatorRequest calculatorRequest;

    @BeforeEach
    public void setUp() {
        calculatorRequest = new CalculatorRequest(new BigDecimal(2), new BigDecimal(3), "SUM");
    }


    @Test
    void shouldGetA() {
        BigDecimal expected = new BigDecimal(2);
        BigDecimal actual = calculatorRequest.getA();
        assertEquals(expected, actual);
    }

    @Test
    void shouldGetB() {
        BigDecimal expected = new BigDecimal(3);
        BigDecimal actual = calculatorRequest.getB();
        assertEquals(expected, actual);
    }

    @Test
    void shouldGetOperation() {
        String expected = "SUM";
        String actual = calculatorRequest.getOperation();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestToString() {
        String actual = calculatorRequest.toString();
        assertTrue(actual.contains("CalculatorRequest"));
        assertTrue(actual.contains("a=2"));
        assertTrue(actual.contains("b=3"));
        assertTrue(actual.contains("operation='SUM'"));
    }

}
