package com.br.paymentproviderwithkafka.service;

import com.br.paymentproviderwithkafka.controller.dto.PaymentRequestDto;
import com.br.paymentproviderwithkafka.controller.dto.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto);
    PaymentResponseDto verifyPayment(String paymentId);
}
