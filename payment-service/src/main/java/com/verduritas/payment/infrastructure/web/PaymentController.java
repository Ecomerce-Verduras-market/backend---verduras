package com.verduritas.payment.infrastructure.web;

import com.verduritas.payment.application.PaymentResponse;
import com.verduritas.payment.application.PaymentService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    List<PaymentResponse> findAll() {
        return paymentService.findAll();
    }
}
