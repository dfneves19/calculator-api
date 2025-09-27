package com.spring.calc.rest.config;


import com.spring.calc.shared.dto.CalculatorResponse;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    // Topic Creation
    @Bean
    public NewTopic calculatorRequests() {
        return new NewTopic("calculator-requests", 1, (short) 1);
    }

    @Bean
    public NewTopic calculatorResponses() {
        return new NewTopic("calculator-responses", 1, (short) 1);
    }


    // Producer Configuration
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    // Consumer Configuration
    @Bean
    public ConsumerFactory<String, CalculatorResponse> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "rest-group"); // unique group id for REST
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.spring.calc.shared.dto");
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CalculatorResponse> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CalculatorResponse> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // ReplyingKafkaTemplate
    @Bean
    public ReplyingKafkaTemplate<String, Object, CalculatorResponse> replyingKafkaTemplate(
            ProducerFactory<String, Object> pf,
            ConcurrentKafkaListenerContainerFactory<String, CalculatorResponse> factory) {

        // reply container listens to responses
        ConcurrentMessageListenerContainer<String, CalculatorResponse> replyContainer = factory.createContainer("calculator-responses");
        replyContainer.getContainerProperties().setGroupId("rest-reply-group");

        return new ReplyingKafkaTemplate<>(pf, replyContainer);
    }

}
