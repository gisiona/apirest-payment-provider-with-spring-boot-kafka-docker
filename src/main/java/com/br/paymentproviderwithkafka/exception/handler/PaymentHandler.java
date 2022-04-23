package com.br.paymentproviderwithkafka.exception.handler;

import com.br.paymentproviderwithkafka.exception.FieldErrorException;
import com.br.paymentproviderwithkafka.exception.PaymentResponseException;
import com.br.paymentproviderwithkafka.exception.PaymentRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class PaymentHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(MethodArgumentNotValidException ex) {
        log.info("Erro MethodArgumentNotValidException: {}", ex);
        List<FieldErrorException> response = new ArrayList<FieldErrorException>();
        List<FieldError> fielErros = ex.getBindingResult().getFieldErrors();
        fielErros.forEach(e -> {
            String msgErro = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            FieldErrorException exception = FieldErrorException
                    .builder()
                    .campo(e.getField())
                    .erro(msgErro)
                    .statusCode(HttpStatus.BAD_REQUEST.toString())
                    .data(LocalDateTime.now())
                    .build();
            response.add(exception);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PaymentRuntimeException.class)
    public ResponseEntity<?> handle(PaymentRuntimeException ex) {
        String msgErro = ex.getErro();

        PaymentResponseException response = PaymentResponseException
                .builder()
                .data(LocalDateTime.now())
                .erro(msgErro)
                .statusCode(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
