package com.parag.OrderService.external.client;

import com.parag.OrderService.exceptions.CustomException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
* @FeignClient(name="<Name-of-service>/<controller-mapping>")
* @CircuitBreaker(name = "Name of circuit breaker", fallbackMethod = "method name in case of fallback")
* */
@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name="ProductService/product")
public interface ProductService {

    @PutMapping("/reduceQuantity/{id}")
    ResponseEntity<Void> reduceQuantity(
            @PathVariable("id") long productId,
            @RequestParam long quantity);

    /*
     * This fallback method will be used by circuit breaker in case product service
     * is down.
     * */
    default void fallback(Exception e) {
        throw new CustomException("Product Service is down", "UNAVAILABLE", 500);
    }

}
