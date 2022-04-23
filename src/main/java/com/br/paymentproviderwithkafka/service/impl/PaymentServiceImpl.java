package com.br.paymentproviderwithkafka.service.impl;

import com.br.paymentproviderwithkafka.controller.dto.PaymentRequestDto;
import com.br.paymentproviderwithkafka.controller.dto.PaymentResponseDto;
import com.br.paymentproviderwithkafka.controller.dto.StatusPayment;
import com.br.paymentproviderwithkafka.exception.PaymentProducerKafkaException;
import com.br.paymentproviderwithkafka.exception.PaymentRuntimeException;
import com.br.paymentproviderwithkafka.integration.kafka.producer.PaymentProducerKafka;
import com.br.paymentproviderwithkafka.repository.PaymentRepository;
import com.br.paymentproviderwithkafka.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service @RequiredArgsConstructor @Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final ObjectMapper mapper;
    private final PaymentProducerKafka paymentProducerKafka;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto) {
        final String paymentId = UUID.randomUUID().toString();
        log.info("Processando paymentId {}", paymentId);
        try {
            PaymentResponseDto response = PaymentResponseDto
                    .builder()
                    .paymentId(paymentId)
                    .dataPayment(LocalDateTime.now())
                    .statusPayment(StatusPayment.PROCESSANDO)
                    .descricaoPedido(paymentRequestDto.getDescricaoPedido())
                    .nomeCliente(paymentRequestDto.getNomeCliente())
                    .numeroPedido(paymentRequestDto.getNumeroPedido())
                    .valorTotalPedido(paymentRequestDto.getValorTotalPedido().setScale(2, BigDecimal.ROUND_UP))
                    .build();

            log.info("Convertendo em json o paymentId {}", paymentId);
            String data = mapper.writeValueAsString(response);

            this.paymentProducerKafka.send(paymentId, data);

            return response;
        } catch (PaymentProducerKafkaException paymentProducerKafkaException) {
            throw new PaymentRuntimeException(paymentProducerKafkaException.getErro());
        } catch (JsonProcessingException jsonProcessingException) {
            log.error("Erro ao converter em json paymentId {}. Detalhes: {}", paymentId, jsonProcessingException);
            throw new PaymentRuntimeException("Erro ao converter em json o payment");
        } catch (Exception ex) {
            log.error("Erro ao processar paymentId {}. Detalhes: {}", paymentId, ex);
            throw new PaymentRuntimeException("Erro ao processar payment");
        }
    }

    @Override
    public PaymentResponseDto verifyPayment(String paymentId) {
        try {
            PaymentResponseDto paymentResponse = paymentRepository.verifyPayment(paymentId);
            return paymentResponse;
        } catch (Exception ex) {
            log.error("Erro ao consultar paymentId {}. Detalhes: {}", paymentId, ex);
            throw new PaymentRuntimeException("Erro ao consultar payment");
        }
    }
}
