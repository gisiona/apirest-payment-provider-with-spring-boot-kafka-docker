package com.br.paymentproviderwithkafka.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FieldErrorException {
    @JsonProperty("campo")
    private String campo;

    @JsonProperty("mensagem")
    private String erro;

    @JsonProperty("status")
    private String statusCode;

    @JsonProperty("timestamp")
    private LocalDateTime data;
}
