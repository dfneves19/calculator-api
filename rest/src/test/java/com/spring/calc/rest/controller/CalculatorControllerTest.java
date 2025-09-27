package com.spring.calc.rest.controller;

import com.spring.calc.shared.dto.CalculatorResponse;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.messaging.Message;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CalculatorControllerTest {

    private ReplyingKafkaTemplate<String, Object, CalculatorResponse> replyingKafkaTemplate;

    private CalculatorController calculatorController;

    @BeforeEach
    void setUp() {
        replyingKafkaTemplate = mock(ReplyingKafkaTemplate.class);
        calculatorController = new CalculatorController(replyingKafkaTemplate);
    }

    @Test
    void testSum() throws Exception {
        BigDecimal one = new BigDecimal("1");
        BigDecimal two = new BigDecimal("2");
        BigDecimal three = new BigDecimal("3");
        CalculatorResponse calculatorResponse = new CalculatorResponse(three, true);
        ConsumerRecord<String, CalculatorResponse> consumerRecord =
                new ConsumerRecord<>("calculator-responses", 0, 0L, null, calculatorResponse);

        RequestReplyFuture<String, Object, CalculatorResponse> future = mock(RequestReplyFuture.class);
        when(replyingKafkaTemplate.sendAndReceive(any(ProducerRecord.class))).thenReturn(future);
        when(future.get()).thenReturn(consumerRecord);

        ResponseEntity<CalculatorResponse> response = calculatorController.sum(one, two);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(three, response.getBody().getResult());
    }

    @Test
    void testSubtraction() throws Exception {
        BigDecimal three = new BigDecimal("3");
        BigDecimal two = new BigDecimal("2");
        BigDecimal one = new BigDecimal("1");
        CalculatorResponse calculatorResponse = new CalculatorResponse(one, true);
        ConsumerRecord<String, CalculatorResponse> consumerRecord =
                new ConsumerRecord<>("calculator-responses", 0, 0L, null, calculatorResponse);

        RequestReplyFuture<String, Object, CalculatorResponse> future = mock(RequestReplyFuture.class);
        when(replyingKafkaTemplate.sendAndReceive(any(ProducerRecord.class))).thenReturn(future);
        when(future.get()).thenReturn(consumerRecord);

        ResponseEntity<CalculatorResponse> response = calculatorController.subtraction(three, two);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(one, response.getBody().getResult());
    }

    @Test
    void testMultiplication() throws Exception {
        BigDecimal three = new BigDecimal("3");
        BigDecimal two = new BigDecimal("2");
        BigDecimal six = new BigDecimal("6");
        CalculatorResponse calculatorResponse = new CalculatorResponse(six, true);
        ConsumerRecord<String, CalculatorResponse> consumerRecord =
                new ConsumerRecord<>("calculator-responses", 0, 0L, null, calculatorResponse);

        RequestReplyFuture<String, Object, CalculatorResponse> future = mock(RequestReplyFuture.class);
        when(replyingKafkaTemplate.sendAndReceive(any(ProducerRecord.class))).thenReturn(future);
        when(future.get()).thenReturn(consumerRecord);

        ResponseEntity<CalculatorResponse> response = calculatorController.multiplication(three, two);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(six, response.getBody().getResult());
    }

    @Test
    void testDivision() throws Exception {
        BigDecimal six = new BigDecimal("6");
        BigDecimal three = new BigDecimal("3");
        BigDecimal two = new BigDecimal("2");
        CalculatorResponse calculatorResponse = new CalculatorResponse(two, true);
        ConsumerRecord<String, CalculatorResponse> consumerRecord =
                new ConsumerRecord<>("calculator-responses", 0, 0L, null, calculatorResponse);

        RequestReplyFuture<String, Object, CalculatorResponse> future = mock(RequestReplyFuture.class);
        when(replyingKafkaTemplate.sendAndReceive(any(ProducerRecord.class))).thenReturn(future);
        when(future.get()).thenReturn(consumerRecord);

        ResponseEntity<CalculatorResponse> response = calculatorController.division(six, three);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(two, response.getBody().getResult());
    }

    @Test
    void testDivisionByZero() throws Exception {
        BigDecimal one = new BigDecimal("1");
        BigDecimal zero = BigDecimal.ZERO;

        //When Divided by Zero, Calculator send empty response
        CalculatorResponse calculatorResponse = new CalculatorResponse(zero, false);
        ConsumerRecord<String, CalculatorResponse> consumerRecord =
                new ConsumerRecord<>("calculator-responses", 0, 0L, null, calculatorResponse);

        RequestReplyFuture<String, Object, CalculatorResponse> future = mock(RequestReplyFuture.class);
        when(replyingKafkaTemplate.sendAndReceive(any(ProducerRecord.class))).thenReturn(future);
        when(future.get()).thenReturn(consumerRecord);

        ResponseEntity<CalculatorResponse> response = calculatorController.division(one, zero);

        System.out.println(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }




}
