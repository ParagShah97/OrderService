package com.parag.OrderService.config;

import com.parag.OrderService.external.decoder.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*
* This configuration class will tell Spring to use custom decoder rather than native
* ErrorDecoder. Here we need to create a method with return type ErrorDecoder. The method
* will return an instance of CustomErrordecoder.
* */
@Configuration
public class FeignConfig {

    @Bean
    ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}
