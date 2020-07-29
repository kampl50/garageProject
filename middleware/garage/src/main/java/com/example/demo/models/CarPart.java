package com.example.demo.models;


import lombok.Data;

@Data
public class CarPart {

    private String name;
    private double price;

    public CarPart(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
