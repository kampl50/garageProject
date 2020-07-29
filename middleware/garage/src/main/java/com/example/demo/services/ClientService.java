package com.example.demo.services;


import com.example.demo.models.Notification;
import com.example.demo.models.Payment;
import com.example.demo.models.Person;
import com.example.demo.models.Worker;
import com.example.demo.repositories.NotificationRepository;
import com.example.demo.repositories.PaymentRepository;
import com.example.demo.repositories.PersonRepository;
import com.example.demo.repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    WorkerRepository workerRepository;

    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public List<Notification> getNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(String id) {
        return notificationRepository.findById(id).get();
    }

    public void editNotification(Notification updatedNotification) {
        notificationRepository.deleteById(updatedNotification.getId());
        notificationRepository.save(updatedNotification);
    }

    public void addWorkerToNotification(String workerId, String notificationId) {
        Worker worker = workerRepository.findById(workerId).get();

        Notification notification = notificationRepository.findById(notificationId).get();
        notification.getWorkersIds().add(workerId);
        notification.getNameAndSurnameWorkers().add(worker.getName() + " " + worker.getSurname());

        notificationRepository.deleteById(notificationId);
        notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsByClientId(String id) {
        return notificationRepository.findAll().stream().filter(n -> n.getClientId().equals(id)).collect(Collectors.toList());
    }

    public List<Notification> getNotificationsByWorkerId(String id) {
       return notificationRepository.findAll().stream().filter(w->w.getWorkersIds().parallelStream().anyMatch(n->n.equals(id))).collect(Collectors.toList());
    }

    private static Person getPersonByLogin(Person person, PersonRepository personRepository) {

        return personRepository.findAll().stream().filter(p -> p.getLogin().equals(person.getLogin())).findFirst().get();
    }

    public static String getIdByLoginAndPassword(Person person, PersonRepository personRepository) {
        Person personWithCheckedLogin = getPersonByLogin(person, personRepository);

        if (personWithCheckedLogin.getLogin().equals(person.getLogin()) && personWithCheckedLogin.getPassword().equals(person.getPassword()))
            return personWithCheckedLogin.getId();
        else
            return "INCORRECT_DATES";
    }

    public List<Payment> getPaymentByClient(String id) {
        return paymentRepository.findAll().stream().filter(p -> p.getClientID().equals(id)).collect(Collectors.toList());
    }
}
