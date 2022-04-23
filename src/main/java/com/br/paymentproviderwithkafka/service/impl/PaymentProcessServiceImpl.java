package com.br.paymentproviderwithkafka.service.impl;

import com.br.paymentproviderwithkafka.controller.dto.PaymentResponseDto;
import com.br.paymentproviderwithkafka.exception.PaymentRuntimeException;
import com.br.paymentproviderwithkafka.repository.PaymentRepository;
import com.br.paymentproviderwithkafka.service.PaymentProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service @Slf4j
@RequiredArgsConstructor
public class PaymentProcessServiceImpl implements PaymentProcess {

    private final PaymentRepository paymentRepository;
    private final ObjectMapper mapper;

    @Override
    public void savePayment(String payment) {
        log.info("Processando pagamento recebido do kafka");
        try {
            log.info("Convertendo mensagem json em objeto Payment");
            PaymentResponseDto paymentSaving = mapper.readValue(payment, PaymentResponseDto.class);

            log.info("Mensagem json convertida objeto Payment {}", paymentSaving);
            paymentRepository.createPayment(paymentSaving.getPaymentId(), paymentSaving);
        } catch (Exception ex) {
            log.error("Erro ao gravar no banco o payment. Detalhes: {}", ex);
            throw new PaymentRuntimeException("Erro ao gravar no banco o payment");
        }
    }
}
