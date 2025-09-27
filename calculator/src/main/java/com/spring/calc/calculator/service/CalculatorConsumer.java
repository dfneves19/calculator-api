package com.spring.calc.calculator.service;

import com.spring.calc.shared.dto.CalculatorRequest;
import com.spring.calc.shared.dto.CalculatorResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculatorConsumer {

    private final KafkaTemplate<String, CalculatorResponse> kafkaTemplate;
    private final CalculatorService calculatorService;

    public CalculatorConsumer(KafkaTemplate<String, CalculatorResponse> kafkaTemplate,
                              CalculatorService calculatorService) {
        this.kafkaTemplate = kafkaTemplate;
        this.calculatorService = calculatorService;
    }

    @KafkaListener(topics = "calculator-requests", groupId = "calculator-group")
    public void listen(CalculatorRequest request,
                       @Header(KafkaHeaders.REPLY_TOPIC) String replyTopic,
                       @Header(KafkaHeaders.CORRELATION_ID) byte[] correlationId) {

        // compute result
        String operation = request.getOperation();
        BigDecimal a  = request.getA();
        BigDecimal b = request.getB();

        BigDecimal result;
        boolean success = true;
        try {
            result = switch (operation) {
                case "SUM" -> calculatorService.sum(a, b);
                case "SUBTRACTION" -> calculatorService.subtract(a, b);
                case "DIVISION" -> calculatorService.divide(a, b);
                case "MULTIPLICATION" -> calculatorService.multiply(a, b);
                default -> BigDecimal.ZERO;
            };
        } catch (Exception ex) {
            result = BigDecimal.ZERO;
            success = false;
        }

        CalculatorResponse response = new CalculatorResponse(result, success);

        // build message with correlationId + replyTopic
        Message<CalculatorResponse> message = MessageBuilder
                .withPayload(response)
                .setHeader(KafkaHeaders.TOPIC, replyTopic)
                .setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
                .build();

        kafkaTemplate.send(message);
    }
}
