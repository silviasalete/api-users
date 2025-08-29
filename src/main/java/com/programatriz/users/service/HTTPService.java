package com.programatriz.users.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.programatriz.users.api.handles.validator.Context;
import com.programatriz.users.api.handles.validator.SendEmailValidation;
import com.programatriz.users.message.Producer;
import com.programatriz.users.model.SendEmail;
import com.programatriz.users.model.SendEmailQueue;
import com.programatriz.users.model.User;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class HTTPService {

    private Producer producer;

    @Value("${api.email.url}")
    private String apiEmailUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailValidation.class);

    @CircuitBreaker(name = "apiEmail", fallbackMethod = "fallbackCliente")
    public HttpResponse<String> requestAPIEmail(SendEmail sendEmail, Producer producer){
        try (HttpClient httpClient = HttpClient.newBuilder().build()) {
            ObjectMapper objectMapper = new ObjectMapper();
            var bodyRequest = HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(sendEmail));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiEmailUrl))
                    .header("Content-Type", "application/json")
                    .POST(bodyRequest)
                    .build();
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            LOGGER.error("CONNECTION FAIL {} IS NOT WORKING....",apiEmailUrl);
            throw new RuntimeException(e);
        }
    }

    public HttpResponse<String> fallbackCliente(SendEmail sendEmail, Producer producer, Throwable t){

        LOGGER.error("CIRCUIT BREAKER CALLED FALLBACK METHOD. Throwable: {}",t.getMessage());
        // TODO Retorne uma mensagem pro usuario falando que não foi possivel enviar o e-mail de validação de conta. aguarde
        // TODO Registrar falha
        producer.sendEmailValidFail(new SendEmailQueue(sendEmail.to(), sendEmail.typeEmail()));
        return null;
    }
}
