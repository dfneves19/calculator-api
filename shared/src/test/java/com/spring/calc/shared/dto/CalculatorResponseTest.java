package com.spring.calc.shared.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CalculatorResponseTest {

    @Test
    void shouldGetResult() {
        CalculatorResponse calculatorResponse = new CalculatorResponse(new BigDecimal(42), true);

        BigDecimal expected = new BigDecimal(42);
        BigDecimal actual = calculatorResponse.getResult();
        assertEquals(expected, actual);
    }

    @Test
    void shouldGetDefaultResultConstructor() {
        CalculatorResponse calculatorResponse = new CalculatorResponse();
        BigDecimal actual = calculatorResponse.getResult();
        assertNull(actual);
    }
}
