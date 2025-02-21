package com.parag.OrderService.external.client;

import com.parag.OrderService.exceptions.CustomException;
import com.parag.OrderService.external.request.PaymentRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/*
 * @FeignClient(name="<Name-of-service>/<controller-mapping>")
 * */
@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name="PaymentService/payment")
public interface PaymentService {
    @PostMapping
    ResponseEntity<Long> makePayment(@RequestBody PaymentRequest paymentRequest);

    /*
    * This fallback method will be used by circuit breaker in case payment service
    * is down.
    * */
    default void fallback(Exception e) {
        throw new CustomException("Payment Service is down", "UNAVAILABLE", 500);
    }
}
