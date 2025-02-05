package com.parag.OrderService.services;

import com.parag.OrderService.entity.Order;
import com.parag.OrderService.model.OrderRequest;
import com.parag.OrderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// The below section is for logging using Logger, for me Lombok is not working.
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;

@Service
public class OrderServiceImpl implements OrderService{
    private final Logger log = LogManager.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        // Things we need to do here, Order Entity: save the data with
        // status as order created.
        // Product Service: Block product that is reduced the quantity.
        // Payment Service -> Payments (Success, Complete) else Cancelled.
        log.info("Placing Order request {} ", orderRequest);

        Order order = new Order(orderRequest.getProductId(),
                orderRequest.getQuantity(), Instant.now(), "CREATED",
                orderRequest.getTotalAmount());

        order = orderRepository.save(order);

        log.info("Order Placed successfully with ID {}", order.getId());

        return order.getId();
    }
}
