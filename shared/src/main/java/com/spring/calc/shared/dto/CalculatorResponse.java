package com.spring.calc.shared.dto;

import java.math.BigDecimal;

public class CalculatorResponse {
    private BigDecimal result;
    private boolean success;

    public CalculatorResponse() {}

    public CalculatorResponse(BigDecimal result, boolean success) {
        this.result = result;
        this.success = success;
    }

    public BigDecimal getResult() {
        return result;
    }

    public boolean getSuccess() {
        return success;
    }
}
