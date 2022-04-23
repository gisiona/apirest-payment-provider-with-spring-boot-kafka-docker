package com.br.paymentproviderwithkafka.integration.kafka.consumer;

import com.br.paymentproviderwithkafka.service.PaymentProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service @Slf4j @RequiredArgsConstructor
public class PaymentProcessConsumerKafka {

    @Value("${spring.kafka.consumer.payment.topic-name}")
    private static final String TOPICO_NAME = "payment_topic";

    @Value("${spring.kafka.consumer.group-id}")
    private static final String GROUP_ID = "group_id";

    private final PaymentProcess paymentProcess;

    @KafkaListener(topics = TOPICO_NAME, groupId = GROUP_ID)
    public void consumerPayment(String payment) {
        log.info("Mensagem recebida do Kafka: Payment: {}", payment);
        paymentProcess.savePayment(payment);
    }
}
