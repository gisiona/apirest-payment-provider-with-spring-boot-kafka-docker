package com.br.paymentproviderwithkafka.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor @Component
public class DatabaseMemoryProvider implements Database {

    public static final Map<String, String> DATABASE = new ConcurrentHashMap<>();
    private final ObjectMapper mapper;

    @SneakyThrows
    @Override
    public <T> T save(final String key, final T value) {
        final String dataJson = this.mapper.writeValueAsString(value);
        DATABASE.put(key, dataJson);
        return value;
    }

    @Override
    public <T> Optional<T> get(final String key, final Class<T> clazz) {
        final String dataJson = this.DATABASE.get(key);

        return Optional.ofNullable(dataJson)
                .map(data -> {
                    try {
                        return mapper.readValue(data, clazz);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
