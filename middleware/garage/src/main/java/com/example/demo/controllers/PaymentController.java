package com.example.demo.controllers;

import com.example.demo.models.Notification;
import com.example.demo.models.Payment;
import com.example.demo.models.Person;
import com.example.demo.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @GetMapping("/api/admin/allPayments")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/api/admin/personByPayment")
    public Person getPersonByPayment(@RequestParam String paymentId) {
        return paymentService.getPersonByIdFromPayment(paymentId);
    }

    @GetMapping("/api/admin/NotificationByPayment")
    public Notification getNotificationByPayment(@RequestParam String paymentId) {
        return paymentService.getNotificationByIdFromPayment(paymentId);
    }

    @PostMapping("/api/admin/addPayment")
    public void addPayment(@RequestBody Payment payment) {
        paymentService.savePayment(payment);
    }

    @PostMapping("/api/admin/editPayment")
    public void editPayment(@RequestBody Payment payment) {
        paymentService.editPayment(payment);
    }

    @PostMapping("/api/admin/deletePayment")
    public void deletePaymentById(@RequestParam String id) {
        paymentService.deleteById(id);
    }

    @PostMapping("/api/admin/markAsPayed")
    public void markedPaymentAsPayed(@RequestParam String id) {
        paymentService.markAsPayed(id);
    }

}
