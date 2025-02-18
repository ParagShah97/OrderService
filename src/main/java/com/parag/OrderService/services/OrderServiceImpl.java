package com.parag.OrderService.services;

import com.parag.OrderService.entity.Order;
import com.parag.OrderService.external.client.PaymentService;
import com.parag.OrderService.external.client.ProductService;
import com.parag.OrderService.external.request.PaymentRequest;
import com.parag.OrderService.model.OrderRequest;
import com.parag.OrderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// The below section is for logging using Logger, for me Lombok is not working.
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{
    private final Logger log = LogManager.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderRepository orderRepository;

//    These are the external service which we can call from the order service
//    using Feign client.
    @Autowired
    private ProductService productService;
//  This is an external service which we can call from order service using Feign
//  client.
    @Autowired
    private PaymentService paymentService;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        // Things we need to do here, Order Entity: save the data with
        // status as order created.
        // Product Service: Block product that is reduced the quantity.
        // Payment Service -> Payments (Success, Complete) else Cancelled.
        log.info("Placing Order request {} ", orderRequest);

        // Call the product service Inter-service API call to reduce the quantity of product ordered.
        productService.reduceQuantity(
                orderRequest.getProductId(),
                orderRequest.getQuantity());

        log.info("Creating order with status as CREATED.");

        // Create an entity object for Order using Model object.
        Order order = new Order(orderRequest.getProductId(),
                orderRequest.getQuantity(), Instant.now(), "CREATED",
                orderRequest.getTotalAmount());

        // Save the Order entity object to database using JPA.
        order = orderRepository.save(order);

        log.info("Calling payment service to complete the payment");
        PaymentRequest paymentRequest = new PaymentRequest(
                order.getId(),
                order.getAmount(),
                UUID.randomUUID().toString(),
                orderRequest.getPaymentMode());
        String orderStatus = null;
        try {
            paymentService.makePayment(paymentRequest);
            log.info("Payment done successfully, changing the status tu PLACED");
            orderStatus = "PLACED";
        } catch(Exception exception) {
            log.info("Error occurred while placing order, changing the order status to PAYMENT_FAILED ");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("Order Placed successfully with ID {}", order.getId());

        return order.getId();
    }
}
