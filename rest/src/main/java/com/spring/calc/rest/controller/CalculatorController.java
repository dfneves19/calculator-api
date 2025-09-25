package com.spring.calc.rest.controller;

import com.spring.calc.rest.service.CalculatorProducer;
import com.spring.calc.shared.dto.CalculatorRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/calculate")
public class CalculatorController {

    private final CalculatorProducer calculatorProducer;

    public CalculatorController(CalculatorProducer calculatorProducer) {
        this.calculatorProducer = calculatorProducer;
    }


    @GetMapping("/sum")
    public String sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        CalculatorRequest request = new CalculatorRequest(a,b,"SUM");
        calculatorProducer.sendRequest(request);

        return "Request Sent to Kafka";
    }
}
