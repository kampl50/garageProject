package com.example.demo.models;

import com.example.demo.enums.KindNotification;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificationDAO {

    private String clientId;
    private Car clientCar;
    private LocalDate dateVisit;
    private String problemDescription;
    private KindNotification kindNotification;

    public NotificationDAO(String clientId, Car clientCar, LocalDate dateVisit, String problemDescription, KindNotification kindNotification) {
        this.clientId = clientId;
        this.clientCar = clientCar;
        this.dateVisit = dateVisit;
        this.problemDescription = problemDescription;
        this.kindNotification = kindNotification;
    }
}
