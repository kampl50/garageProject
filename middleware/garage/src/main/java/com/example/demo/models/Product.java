package com.example.demo.models;

import lombok.Data;

@Data
public class Product {
    private String name;
    private String unitPrice;
    private String quantity;

    public Product(String name, String unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = "1";
    }

}