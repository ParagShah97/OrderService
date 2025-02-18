package com.parag.OrderService.external.request;

import com.parag.OrderService.model.PaymentMode;

public class PaymentRequest {
    private long orderId;
    private long amount;
    private String referenceNumber;
    private PaymentMode paymentMode;

    public PaymentRequest() {
    }

    public PaymentRequest(long orderId, long amount, String referenceNumber, PaymentMode paymentMode) {
        this.orderId = orderId;
        this.amount = amount;
        this.referenceNumber = !referenceNumber.isEmpty() ? referenceNumber : "";
        this.paymentMode = paymentMode;
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

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }
}
