package com.parag.OrderService.services;

import com.parag.OrderService.model.OrderRequest;
import com.parag.OrderService.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long id);
}
