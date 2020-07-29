package com.example.demo.services;

import com.example.demo.models.Notification;
import com.example.demo.models.Payment;
import com.example.demo.models.Person;
import com.example.demo.repositories.NotificationRepository;
import com.example.demo.repositories.PaymentRepository;
import com.example.demo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    PersonRepository personRepository;

    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public void deleteById(String id) {
        paymentRepository.deleteById(id);
    }

    public Payment getPaymentById(String id) {
        return paymentRepository.findById(id).get();
    }

    public void editPayment(Payment updatedPayment) {
        paymentRepository.save(updatedPayment);
    }

    public void markAsPayed(String id) {
        Payment p = getPaymentById(id);
        p.setPayed(true);

        paymentRepository.deleteById(id);
        paymentRepository.save(p);
    }

    public Person getPersonByIdFromPayment(String id) {
        Payment payment = getPaymentById(id);
        String personId = payment.getClientID();
        return personRepository.findById(personId).get();
    }

    public Notification getNotificationByIdFromPayment(String id) {
        Payment payment = getPaymentById(id);
        String notificationId = payment.getNotificationID();
        return notificationRepository.findById(notificationId).get();
    }

}
