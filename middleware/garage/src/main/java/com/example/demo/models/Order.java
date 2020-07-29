package com.example.demo.models;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private String continueUrl;
    private String extOrderId;
    private String customerIp;
    private String merchantPosId;
    private String description;
    private String currencyCode;
    private String totalAmount;
    private List<Product> products;


    public Order(String continueUrl, String extOrderId, String customerIp, String merchantPosId, String description, String currencyCode, String totalAmount, List<Product> products) {
        this.continueUrl = continueUrl;
        this.extOrderId = extOrderId;
        this.customerIp = customerIp;
        this.merchantPosId = merchantPosId;
        this.description = description;
        this.currencyCode = currencyCode;
        this.totalAmount = totalAmount;
        this.products = products;
    }

}
