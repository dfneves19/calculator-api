package com.spring.calc.rest.controller;

import com.spring.calc.shared.dto.CalculatorRequest;
import com.spring.calc.shared.dto.CalculatorResponse;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/calculate")
public class CalculatorController {

    private final Logger logger = LoggerFactory.getLogger(CalculatorController.class);
    private final ReplyingKafkaTemplate<String, Object, CalculatorResponse> replyingKafkaTemplate;

    public CalculatorController(ReplyingKafkaTemplate<String, Object, CalculatorResponse> replyingKafkaTemplate) {
        this.replyingKafkaTemplate = replyingKafkaTemplate;
    }

    public ResponseEntity<CalculatorResponse> sendCalculation(CalculatorRequest request) throws ExecutionException, InterruptedException {
        // create record for request topic
        ProducerRecord<String, Object> record = new ProducerRecord<>("calculator-requests", request);

        logger.debug("Sending calculator response to request record: {}", record);

        // send and wait for response
        RequestReplyFuture<String, Object, CalculatorResponse> future = replyingKafkaTemplate.sendAndReceive(record);
        CalculatorResponse response = future.get().value();

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/sum")
    public ResponseEntity<CalculatorResponse> sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) throws Exception {

        CalculatorRequest request = new CalculatorRequest(a, b, "SUM");
        return sendCalculation(request);
    }

    @GetMapping("/subtraction")
    public ResponseEntity<CalculatorResponse> subtraction(@RequestParam BigDecimal a, @RequestParam BigDecimal b) throws Exception {

        CalculatorRequest request = new CalculatorRequest(a, b, "SUBTRACTION");
        return sendCalculation(request);
    }

    @GetMapping("/multiplication")
    public ResponseEntity<CalculatorResponse> multiplication(@RequestParam BigDecimal a, @RequestParam BigDecimal b) throws Exception {

        CalculatorRequest request = new CalculatorRequest(a, b, "MULTIPLICATION");
        return sendCalculation(request);
    }

    @GetMapping("/division")
    public ResponseEntity<CalculatorResponse> division(@RequestParam BigDecimal a, @RequestParam BigDecimal b) throws Exception {

        CalculatorRequest request = new CalculatorRequest(a, b, "DIVISION");
        return sendCalculation(request);
    }
}
