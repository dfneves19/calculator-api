package com.spring.calc.rest.controller;

import com.spring.calc.shared.dto.CalculatorRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

public class CalculatorController {

    @GetMapping(value= "/calculate/sum")
    public void sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        CalculatorRequest request = new CalculatorRequest(a,b,"SUM");

    }
}
