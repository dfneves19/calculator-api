package com.spring.calc.shared.dto;

import java.math.BigDecimal;

public class CalculatorRequest {
    private BigDecimal a;
    private BigDecimal b;
    private String operation;

    public CalculatorRequest() {}

    public CalculatorRequest(BigDecimal a, BigDecimal b, String operation) {
        this.a = a;
        this.b = b;
        this.operation = operation;
    }

    public BigDecimal getA() {
        return a;
    }

    public BigDecimal getB() {
        return b;
    }

    public String getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return "CalculatorRequest{" +
                "a=" + a +
                ", b=" + b +
                ", operation='" + operation + '\'' +
                '}';
    }
}
