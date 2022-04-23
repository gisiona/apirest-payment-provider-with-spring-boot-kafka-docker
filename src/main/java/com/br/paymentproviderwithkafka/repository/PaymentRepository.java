package com.br.paymentproviderwithkafka.repository;

import com.br.paymentproviderwithkafka.controller.dto.PaymentResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRepository {

    private final Database database;

    public PaymentResponseDto createPayment(String paymentId, PaymentResponseDto payment) {
        log.info("Salvando no banco de dados o paymentID {}", paymentId);
        return this.database.save(paymentId, payment);
    }

    public PaymentResponseDto verifyPayment(String paymentId) {
        log.info("consultando no banco de dados o paymentID {}", paymentId);
        final Optional<PaymentResponseDto> paymentResponse = this.database.get(paymentId, PaymentResponseDto.class);
        return paymentResponse.isPresent() ? paymentResponse.get() : PaymentResponseDto.builder().build();
    }
}
