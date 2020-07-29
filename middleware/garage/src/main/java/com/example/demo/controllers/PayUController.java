package com.example.demo.controllers;


import com.example.demo.enums.StatusNotification;
import com.example.demo.models.Notification;
import com.example.demo.models.Payment;
import com.example.demo.payments.PayUUtils;
import com.example.demo.services.ClientService;
import com.example.demo.services.PaymentService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PayUController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    ClientService clientService;

    @PostMapping("/api/pay")
    public ResponseEntity pay(@RequestBody Payment payment, HttpServletRequest request) throws com.fasterxml.jackson.core.JsonProcessingException, JSONException {

        Notification notification = clientService.getNotificationById(payment.getNotificationID());
        notification.setStatusNotification(StatusNotification.READY);
        clientService.editNotification(notification);
        paymentService.savePayment(payment);

        return PayUUtils.getUrlToPay(payment, request);
    }
}
