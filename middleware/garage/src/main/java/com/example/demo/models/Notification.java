package com.example.demo.models;

import com.example.demo.enums.KindNotification;
import com.example.demo.enums.StatusNotification;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Data
public class Notification {
    @Id
    private String id;
    private String clientId;
    private List<String> workersIds;
    private List<String> nameAndSurnameWorkers;
    private Car clientCar;
    private List<CarPart> partsToOrder;
    private LocalDate dateVisit;
    private String problemDescription;
    private KindNotification kindNotification;
    private StatusNotification statusNotification;
    private double price;

    public Notification(String clientId, List<String> workersIds, List<String> nameAndSurnameWorkers, Car clientCar, List<CarPart> partsToOrder,
                        LocalDate dateVisit, String problemDescription, KindNotification kindNotification, StatusNotification statusNotification) {
        this.clientId = clientId;
        this.workersIds = workersIds;
        this.nameAndSurnameWorkers = nameAndSurnameWorkers;
        this.clientCar = clientCar;
        this.partsToOrder = partsToOrder;
        this.dateVisit = dateVisit;
        this.problemDescription = problemDescription;
        this.kindNotification = kindNotification;
        this.statusNotification = statusNotification;
        this.price = 0;
    }

    public static Notification toNotificationConverter(NotificationDAO notificationDAO){
        return new Notification(notificationDAO.getClientId(),new LinkedList<String>(),new LinkedList<String>(),notificationDAO.getClientCar(),new LinkedList<CarPart>(),notificationDAO.getDateVisit(),
                notificationDAO.getProblemDescription(),notificationDAO.getKindNotification(),StatusNotification.NEW);
    }
}
