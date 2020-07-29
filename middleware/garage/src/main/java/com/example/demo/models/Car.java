package com.example.demo.models;

import com.example.demo.enums.Engine;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Car {

    private String mark;
    private String model;
    private LocalDate dateProduction;
    private Engine kindEngine;
    private int power;

    public Car(String mark, String model, LocalDate dateProduction, Engine kindEngine, int power) {
        this.mark = mark;
        this.model = model;
        this.dateProduction = dateProduction;
        this.kindEngine = kindEngine;
        this.power = power;
    }
}
