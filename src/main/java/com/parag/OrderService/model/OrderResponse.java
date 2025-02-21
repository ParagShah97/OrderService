package com.parag.OrderService.model;

import com.parag.OrderService.external.response.PaymentResponse;
import lombok.Data;

import java.time.Instant;

@Data
public class OrderResponse {
    private long orderId;
    private long amount;
    private String orderStatus;
    private Instant orderDate;
    private ProductDetails productDetails;
    private PaymentResponse paymentResponse;

    public OrderResponse() {
    }

    public OrderResponse(
            long orderId,
            long amount,
            String orderStatus,
            Instant orderDate,
            ProductDetails productDetails,
            PaymentResponse paymentResponse) {
        this.orderId = orderId;
        this.amount = amount;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.productDetails = productDetails;
        this.paymentResponse = paymentResponse;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    public PaymentResponse getPaymentResponse() {
        return paymentResponse;
    }

    public void setPaymentResponse(PaymentResponse paymentResponse) {
        this.paymentResponse = paymentResponse;
    }
}
