package com.br.paymentproviderwithkafka.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequestDto {

    @JsonProperty("numero_pedido")
    private String numeroPedido;

    @JsonProperty("descricao_pedido")
    private String descricaoPedido;

    @JsonProperty("valor_total_pedido")
    private BigDecimal valorTotalPedido;

    @JsonProperty("nome_cliente")
    private String nomeCliente;
}
