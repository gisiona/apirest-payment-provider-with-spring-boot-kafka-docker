package com.br.paymentproviderwithkafka.service;

public interface PaymentProcess {
    void savePayment(String payment);
}
