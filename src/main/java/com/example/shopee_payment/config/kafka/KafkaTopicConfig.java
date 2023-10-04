package com.example.shopee_payment.config.kafka;

import com.example.shopee_payment.variable.KafkaVariable;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic initPayForOrderTopic() {
        return new NewTopic(KafkaVariable.PAY_FOR_ORDER_TOPIC, 2, (short) 1);
    }

    @Bean
    public NewTopic initUpdateOrderAfterPaid() {
        return new NewTopic(KafkaVariable.UPDATE_ORDER_AFTER_PAID, 2, (short) 1);
    }
}
