package com.smartparkinglot.backend.controller;

import com.smartparkinglot.backend.DTO.PaymentDetailsDTO;
import com.smartparkinglot.backend.DTO.PaymentResponseDTO;
import com.smartparkinglot.backend.service.EmailService;
import com.smartparkinglot.backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class StripeAPIController {
    private final PaymentService paymentService;
    private final EmailService emailService;

    @Autowired
    public StripeAPIController(PaymentService paymentService, EmailService emailService) {
        this.paymentService = paymentService;
        this.emailService = emailService;
    }

    @PostMapping("/create-payment-intent")
    public PaymentResponseDTO createPaymentIntent(@RequestBody PaymentDetailsDTO paymentRequest) {
        return paymentService.createPaymentIntent(paymentRequest);
    }

}