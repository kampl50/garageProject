package com.example.demo.controllers;

import com.example.demo.enums.Role;
import com.example.demo.models.Person;
import com.example.demo.models.Worker;
import com.example.demo.repositories.PersonRepository;
import com.example.demo.services.WorkerService;
import com.example.demo.utils.LoginRegistrationUtils;
import com.example.demo.utils.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRegistrationController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private WorkerService workerService;

    private LoginRegistrationUtils loginRegistrationUtils = new LoginRegistrationUtils();
    private Security security = new Security();

    @PostMapping("/api/logIn")
    public ResponseEntity login(@RequestBody Person person) {
        return security.getToken(person, personRepository);
    }

    @PostMapping("/api/register")
    public String register(@RequestBody Person person) {
        if (person.getRole().equals(Role.WORKER)) {
            String id = workerService.save(Person.toWorkerConverter(person));
            return loginRegistrationUtils.register(person, personRepository, id);
        }
        return loginRegistrationUtils.register(person, personRepository);
    }

}
