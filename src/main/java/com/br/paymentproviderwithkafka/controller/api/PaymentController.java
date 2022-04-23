package com.br.paymentproviderwithkafka.controller.api;

import com.br.paymentproviderwithkafka.controller.dto.PaymentRequestDto;
import com.br.paymentproviderwithkafka.controller.dto.PaymentResponseDto;
import com.br.paymentproviderwithkafka.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private static final String BASE_PATH_URL = "http://localhost:1020/api/v1/payments/";
    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        log.info("Payload entrada create payment; {}", paymentRequestDto);

        PaymentResponseDto payment = paymentService.createPayment(paymentRequestDto);
        HttpHeaders headers = getHttpHeaders(payment.getPaymentId());

        log.info("Payload saida create paymentId {}, Payment: {}", payment.getPaymentId(), payment);
        return new ResponseEntity<>(payment, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id_payment}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> verifyPayment(@PathVariable("id_payment") String idPayment) {
        PaymentResponseDto payment = paymentService.verifyPayment(idPayment);
        return ResponseEntity.ok(payment);
    }

    private HttpHeaders getHttpHeaders(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setLocation(URI.create(BASE_PATH_URL + url));
        return headers;
    }
}
