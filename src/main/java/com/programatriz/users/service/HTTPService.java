package com.programatriz.users.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programatriz.users.message.Producer;
import com.programatriz.users.model.SendEmail;
import com.programatriz.users.model.SendEmailQueue;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
public class HTTPService {

    private Producer producer;

    @Value("${api.email.url}")
    private String apiEmailUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(HTTPService.class);

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
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200){
                LOGGER.error("[FLOW-NEW-USER] Status code is {} in {}",apiEmailUrl, response.statusCode());
                throw new RuntimeException();
            }
            return response;
        } catch (Exception e) {
            LOGGER.error("[FLOW-NEW-USER] Connection with {} service isn't working",apiEmailUrl);
            throw new RuntimeException(e);
        }
    }

    public HttpResponse<String> fallbackCliente(SendEmail sendEmail, Producer producer, Throwable t){
        // TODO Test if sending email fail, sending message to queue and while API-EMAIL to return should send email to client
        LOGGER.info("[FLOW-NEW-USER] Circuit break method called because {} ",t.getMessage());
        producer.sendEmailValidFail(new SendEmailQueue(sendEmail.to(), sendEmail.typeEmail()));
        return null;
    }
}
