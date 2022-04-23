package com.br.paymentproviderwithkafka.integration.kafka.producer;

import com.br.paymentproviderwithkafka.exception.PaymentProducerKafkaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentProducerKafka {

    @Value("${spring.kafka.producer.payment.topic-name}")
    private static final String TOPICO_NAME = "payment_topic";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String paymentId, String paymentRequestDto) {
        try {
            log.info("Enviando para o kafka o paymentId {}", paymentId);
            this.kafkaTemplate.send(TOPICO_NAME, paymentId, paymentRequestDto);

            log.info("Payment enviado com sucesso para o kafka. PaymentId {}, Payload {}",paymentId, paymentRequestDto);
        } catch (Exception exception) {
            log.error("Erro ao enviar para o kafka o paymentId {}. Detalhes: {}", paymentId, exception);
            throw new PaymentProducerKafkaException("Erro ao enviar para o kafka o payment");
        }
    }
}
