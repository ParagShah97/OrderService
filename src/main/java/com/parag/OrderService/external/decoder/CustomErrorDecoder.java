package com.parag.OrderService.external.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parag.OrderService.controller.OrderController;
import com.parag.OrderService.exceptions.CustomException;
import com.parag.OrderService.external.response.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


/*
* CustomErrorDecoder: This class will receive the exception/error from the host service,
* to which we make an API call, example reduceQuantity from place order service.
* */
public class CustomErrorDecoder implements ErrorDecoder {

    private final Logger log = LogManager.getLogger(CustomErrorDecoder.class);
    @Override
    public Exception decode(String s, Response response) {
        // ObjectMapper provides functionality for reading and writing JSON, either to and from basic POJOs (Plain Old Java Objects)
        ObjectMapper objectMapper = new ObjectMapper();

        log.info("::{}", response.request().url());
        log.info("::{}", response.request().headers());

        try {
            // Here, create an object of ErrorResponse same as what is used in Product Service.
            // We used objectMapper readValue to read the response
            ErrorResponse errorResponse
                    = objectMapper.readValue(response.body().asInputStream(),
                    ErrorResponse.class);
            // In Case of error, we will throw a custom exception.
            return new CustomException(errorResponse.getErrorMessage(),
                    errorResponse.getErrorCode(), response.status());
        } catch (IOException e) {
            throw new CustomException("Internal Server Error", "INTERNAL_SERVER_ERROR", 500);
        }
    }
}
