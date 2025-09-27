package com.spring.calc.calculator.service;

import com.spring.calc.shared.dto.CalculatorRequest;
import com.spring.calc.shared.dto.CalculatorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.kafka.support.KafkaHeaders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalculatorConsumerTest {

    private CalculatorService calculatorService;
    private KafkaTemplate<String, CalculatorResponse> kafkaTemplate;

    private CalculatorConsumer calculatorConsumer;

    @BeforeEach
    void setUp() {
        calculatorService = mock(CalculatorService.class);
        kafkaTemplate = mock(KafkaTemplate.class);

        calculatorConsumer = new CalculatorConsumer(kafkaTemplate, calculatorService);
    }

    @Test
    void testSumOperation() {
        BigDecimal five = BigDecimal.valueOf(5);
        BigDecimal three = BigDecimal.valueOf(3);
        BigDecimal eight = BigDecimal.valueOf(8);

        CalculatorRequest request = new CalculatorRequest(five, three, "SUM");

        byte[] correlationId = "123".getBytes();
        String replyTopic = "reply-topic";

        when(calculatorService.sum(five, three)).thenReturn(eight);
        calculatorConsumer.listen(request, replyTopic, correlationId);

        ArgumentCaptor<Message<CalculatorResponse>> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(kafkaTemplate).send(messageCaptor.capture());

        Message<CalculatorResponse> sentMessage = messageCaptor.getValue();
        assertEquals(eight, sentMessage.getPayload().getResult());
        assertTrue(sentMessage.getPayload().getSuccess());
        assertEquals(replyTopic, sentMessage.getHeaders().get(KafkaHeaders.TOPIC));
        assertEquals(correlationId, sentMessage.getHeaders().get(KafkaHeaders.CORRELATION_ID));
    }

    @Test
    void testSubtractionOperation() {
        BigDecimal ten = BigDecimal.valueOf(10);
        BigDecimal four = BigDecimal.valueOf(4);
        BigDecimal six = BigDecimal.valueOf(6);

        CalculatorRequest request = new CalculatorRequest(ten, four, "SUBTRACTION");

        byte[] correlationId = "123".getBytes();
        String replyTopic = "reply-topic";

        when(calculatorService.subtract(ten, four)).thenReturn(six);
        calculatorConsumer.listen(request, replyTopic, correlationId);

        ArgumentCaptor<Message<CalculatorResponse>> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(kafkaTemplate).send(messageCaptor.capture());

        Message<CalculatorResponse> sentMessage = messageCaptor.getValue();
        assertEquals(six, sentMessage.getPayload().getResult());
        assertTrue(sentMessage.getPayload().getSuccess());
        assertEquals(replyTopic, sentMessage.getHeaders().get(KafkaHeaders.TOPIC));
        assertEquals(correlationId, sentMessage.getHeaders().get(KafkaHeaders.CORRELATION_ID));
    }

    @Test
    void testDivisionOperation() {
        BigDecimal six = BigDecimal.valueOf(6);
        BigDecimal two = BigDecimal.valueOf(2);
        BigDecimal three = BigDecimal.valueOf(3);

        CalculatorRequest request = new CalculatorRequest(six, two, "DIVISION");

        byte[] correlationId = "123".getBytes();
        String replyTopic = "reply-topic";

        // Simulate calculatorService throwing ArithmeticException
        when(calculatorService.divide(six, two)).thenReturn(three);

        calculatorConsumer.listen(request, replyTopic, correlationId);

        ArgumentCaptor<Message<CalculatorResponse>> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(kafkaTemplate).send(messageCaptor.capture());

        Message<CalculatorResponse> sentMessage = messageCaptor.getValue();
        assertEquals(three, sentMessage.getPayload().getResult());
        assertTrue(sentMessage.getPayload().getSuccess());
        assertEquals(replyTopic, sentMessage.getHeaders().get(KafkaHeaders.TOPIC));
        assertEquals(correlationId, sentMessage.getHeaders().get(KafkaHeaders.CORRELATION_ID));
    }

    @Test
    void testDivisionByZeroReturnsZero() {
        CalculatorRequest request = new CalculatorRequest(BigDecimal.valueOf(5),  BigDecimal.ZERO, "DIVISION");

        byte[] correlationId = "123".getBytes();
        String replyTopic = "reply-topic";

        // Simulate calculatorService throwing ArithmeticException
        when(calculatorService.divide(BigDecimal.valueOf(5), BigDecimal.ZERO))
                .thenThrow(new ArithmeticException("Division by zero"));

        calculatorConsumer.listen(request, replyTopic, correlationId);

        ArgumentCaptor<Message<CalculatorResponse>> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(kafkaTemplate).send(messageCaptor.capture());

        Message<CalculatorResponse> sentMessage = messageCaptor.getValue();
        assertEquals(sentMessage.getPayload().getResult(),  BigDecimal.ZERO);
        assertFalse(sentMessage.getPayload().getSuccess());
        assertEquals(replyTopic, sentMessage.getHeaders().get(KafkaHeaders.TOPIC));
        assertEquals(correlationId, sentMessage.getHeaders().get(KafkaHeaders.CORRELATION_ID));
    }

    @Test
    void testMultiplicationOperation() {
        BigDecimal ten = BigDecimal.valueOf(10);
        BigDecimal four = BigDecimal.valueOf(4);
        BigDecimal forty = BigDecimal.valueOf(40);

        CalculatorRequest request = new CalculatorRequest(ten, four, "MULTIPLICATION");

        byte[] correlationId = "123".getBytes();
        String replyTopic = "reply-topic";

        when(calculatorService.multiply(ten, four)).thenReturn(forty);
        calculatorConsumer.listen(request, replyTopic, correlationId);

        ArgumentCaptor<Message<CalculatorResponse>> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(kafkaTemplate).send(messageCaptor.capture());

        Message<CalculatorResponse> sentMessage = messageCaptor.getValue();
        assertEquals(forty, sentMessage.getPayload().getResult());
        assertTrue(sentMessage.getPayload().getSuccess());
        assertEquals(replyTopic, sentMessage.getHeaders().get(KafkaHeaders.TOPIC));
        assertEquals(correlationId, sentMessage.getHeaders().get(KafkaHeaders.CORRELATION_ID));
    }
}
