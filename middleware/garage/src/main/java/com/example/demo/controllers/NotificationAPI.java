package com.example.demo.controllers;

import com.example.demo.models.Notification;
import com.example.demo.models.NotificationDAO;
import com.example.demo.models.Payment;
import com.example.demo.services.ClientService;
import com.example.demo.services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationAPI {

    @Autowired
    ClientService clientService;

    @Autowired
    WorkerService workerService;

    @PostMapping("/api/service/orderVisit")
    public void orderNotification(@RequestBody NotificationDAO notificationDAO) {
        Notification notification = Notification.toNotificationConverter(notificationDAO);
        Notification notificationWithWorker = workerService.addWorkerToNotification(notification);
        clientService.saveNotification(notificationWithWorker);
    }

    @PostMapping("/api/service/acceptValuatedOrder")
    public void acceptValuatedOrder(@RequestParam String id, String isAccepted){
        workerService.isConfirmed(id,isAccepted);
    }

    @PostMapping("/api/service/editOrder")
    public void editNotification(@RequestBody Notification notification) {
        clientService.editNotification(notification);
    }

    @GetMapping("/api/service/orders")
    public List<Notification> getNotifications() {
        return clientService.getNotifications();
    }

    @GetMapping("/api/service/ordersById")
    public Notification getNotificationsById(@RequestParam String id) {
        return clientService.getNotificationById(id);
    }

    @GetMapping("/api/service/ordersByClientId")
    public List<Notification> getNotificationsByClientId(@RequestParam String id) {
        return clientService.getNotificationsByClientId(id);
    }

    @GetMapping("/api/service/ordersByWorkerId")
    public List<Notification> getNotificationsByWorkerId(@RequestParam String id) {
        return clientService.getNotificationsByWorkerId(id);
    }

    @GetMapping
    public List<Payment> getPaymentByClientId(@RequestParam String id) {
        return clientService.getPaymentByClient(id);
    }

    @PostMapping("/api/service/addWorker")
    public void addWorkerToNotification(@RequestParam String workerId, @RequestParam String notificationId) {
        clientService.addWorkerToNotification(workerId, notificationId);
    }
}
