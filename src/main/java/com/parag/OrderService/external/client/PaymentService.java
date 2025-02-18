package com.parag.OrderService.external.client;

import com.parag.OrderService.external.request.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/*
 * @FeignClient(name="<Name-of-service>/<controller-mapping>")
 * */
@FeignClient(name="PaymentService/payment")
public interface PaymentService {
    @PostMapping
    ResponseEntity<Long> makePayment(@RequestBody PaymentRequest paymentRequest);
}
