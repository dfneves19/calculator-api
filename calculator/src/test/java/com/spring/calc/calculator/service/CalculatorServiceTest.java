package com.spring.calc.calculator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorServiceTest {
    private CalculatorService calculatorService;

    @BeforeEach
    public void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    public void testAdd() {
        BigDecimal a = new BigDecimal("4");
        BigDecimal b = new BigDecimal("1.5");
        BigDecimal expected = new BigDecimal("5.5");
        BigDecimal result = calculatorService.sum(a, b);
        assertEquals(expected, result);
    }

    @Test
    public void testSubtract() {
        BigDecimal a = new BigDecimal("8");
        BigDecimal b = new BigDecimal("2.25");
        BigDecimal expected = new BigDecimal("5.75");
        BigDecimal result = calculatorService.subtract(a, b);
        assertEquals(expected, result);
    }

    @Test
    public void testMultiply() {
        BigDecimal a = new BigDecimal("3");
        BigDecimal b = new BigDecimal("2.5");
        BigDecimal expected = new BigDecimal("7.5");
        BigDecimal result = calculatorService.multiply(a, b);
        assertEquals(expected, result);
    }

    @Test
    public void testDivide() {
        BigDecimal a = new BigDecimal("10");
        BigDecimal b = new BigDecimal("4");
        BigDecimal expected = new BigDecimal("2.500");
        BigDecimal result = calculatorService.divide(a, b);
        assertEquals(expected, result);
    }

    @Test
    public void testDivideWithPrecision() {
        BigDecimal a = new BigDecimal("1");
        BigDecimal b = new BigDecimal("3");
        BigDecimal expected = new BigDecimal("0.333");
        BigDecimal result = calculatorService.divide(a, b);
        assertEquals(expected, result);
    }

    @Test
    public void testDivideByZero() {
        BigDecimal a = new BigDecimal("10");
        BigDecimal b = new BigDecimal("0");
        ArithmeticException exception = assertThrows(
                ArithmeticException.class,
                () -> calculatorService.divide(a, b)
        );
        assertEquals("Division by zero",exception.getMessage());
    }
}
