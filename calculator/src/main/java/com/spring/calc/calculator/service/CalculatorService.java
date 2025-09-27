package com.spring.calc.calculator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculatorService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public BigDecimal sum(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    public BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    public BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

    public BigDecimal divide(BigDecimal a, BigDecimal b) throws ArithmeticException {
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            logger.error("Divide by zero");
            throw new ArithmeticException("Division by zero");
        } else{
            return a.divide(b,3, RoundingMode.HALF_UP);
        }
    }
}

