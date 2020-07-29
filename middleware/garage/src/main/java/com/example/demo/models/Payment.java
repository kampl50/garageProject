package com.example.demo.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Payment {

    @Id
    private String id;
    private String notificationID;
    private String clientID;
    private String description;
    private String price;
    private boolean isPayed;

    public Payment(String notificationID, String clientID, String description, String price, boolean isPayed) {
        this.notificationID = notificationID;
        this.clientID = clientID;
        this.description = description;
        this.price = price;
        this.isPayed = isPayed;
    }

    public static Product toProductConverter(Payment payment) {
        return new Product(payment.notificationID, payment.getPrice());
    }
}
