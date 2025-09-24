package com.spring.calc.shared.dto;

import java.math.BigDecimal;

public class CalculatorRequest {
    public BigDecimal a;
    public BigDecimal b;
    public String operation;

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

    public void setA(BigDecimal a) {
        this.a = a;
    }

    public void setB(BigDecimal b) {
        this.b = b;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
