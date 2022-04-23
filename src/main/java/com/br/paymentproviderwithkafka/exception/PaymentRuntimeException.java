package com.br.paymentproviderwithkafka.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class PaymentRuntimeException extends RuntimeException {
    private String erro;
}
