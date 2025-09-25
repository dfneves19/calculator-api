package com.spring.calc.rest.service;

import com.spring.calc.shared.dto.CalculatorRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CalculatorProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CalculatorProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendRequest(CalculatorRequest request) {
        kafkaTemplate.send("calculator-requests", request);
    }
}
