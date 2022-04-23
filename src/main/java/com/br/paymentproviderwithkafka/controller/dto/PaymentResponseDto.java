package com.br.paymentproviderwithkafka.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {
    @JsonProperty("codigo_pagamento")
    private String paymentId;

    @JsonProperty("data_criacao_pagamento")
    private LocalDateTime dataPayment;

    @JsonProperty("status_pagamento")
    private StatusPayment statusPayment;

    @JsonProperty("numero_pedido")
    private String numeroPedido;

    @JsonProperty("descricao_pedido")
    private String descricaoPedido;

    @JsonProperty("valor_total_pedido")
    private BigDecimal valorTotalPedido;

    @JsonProperty("nome_cliente")
    private String nomeCliente;
}
