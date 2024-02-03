package com.example.paymentsservice.service;

import com.example.paymentsservice.model.PaymentRequest;
import com.example.paymentsservice.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(long orderId);
}
