package com.br.paymentproviderwithkafka.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data @Builder
public class PaymentResponseException {

    @JsonProperty("mensagem")
    private String erro;

    @JsonProperty("status")
    private String statusCode;

    @JsonProperty("timestamp")
    private LocalDateTime data;
}
