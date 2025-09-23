package com.spring.calc.shared.dto;

import java.math.BigDecimal;

public class CalculatorResponse {
    private BigDecimal result;

    CalculatorResponse(BigDecimal result) {
        this.result = result;
    }

    public BigDecimal getResult() {
        return result;
    }
    public void setResult(BigDecimal result) {
        this.result = result;
    }
}
