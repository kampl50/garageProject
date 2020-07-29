package com.example.demo.models;

import com.example.demo.enums.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Person {

    @Id
    private String id;
    private String login;
    private String password;
    private Role role;
    private String name;
    private String surname;
    private String numberPhone;
    private String addressEmail;

    public Person(String login, String password, Role role, String name, String surname, String numberPhone, String addressEmail) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.numberPhone = numberPhone;
        this.addressEmail = addressEmail;
    }

    public static Worker toWorkerConverter(Person person){
        return new Worker(person.getLogin(),person.getPassword(),person.getRole(),person.getName(),person.getSurname(),person.getNumberPhone(),person.getAddressEmail());
    }
}
