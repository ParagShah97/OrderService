package com.parag.OrderService.external.response;

import com.parag.OrderService.model.PaymentMode;
import lombok.Data;

import java.time.Instant;

/*
* This POJO class will used to get the payment details and send back from an API.
* */
@Data
public class PaymentResponse {

    private long paymentId;
    private long orderId;
    private String status;
    private PaymentMode paymentMode;
    private Instant paymentDate;
    private long amount;

    public PaymentResponse() {
    }

    public PaymentResponse(long paymentId, long orderId, String status, PaymentMode paymentMode, Instant paymentDate, long amount) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.status = status;
        this.paymentMode = paymentMode;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
