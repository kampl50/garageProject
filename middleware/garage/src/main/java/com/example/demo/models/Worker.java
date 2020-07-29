package com.example.demo.models;

import com.example.demo.enums.CarBrand;
import com.example.demo.enums.Role;
import com.example.demo.enums.WorkerStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Worker extends Person{

    private WorkerStatus workerStatus;
    private int numberOfNotifications;
    private int experience;
    private List<CarBrand> preferCarBrands = new ArrayList<>(3);

    public Worker(String login, String password, Role role, String name, String surname, String numberPhone, String addressEmail) {
        super(login, password, role, name, surname, numberPhone, addressEmail);
        this.workerStatus = WorkerStatus.WITHOUT_NOTIFICATIONS;
        this.numberOfNotifications = 0;
        this.experience = 0;
    }
}
