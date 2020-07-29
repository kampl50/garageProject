package com.example.demo.controllers;

import com.example.demo.models.Person;
import com.example.demo.services.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdministratorController {

    @Autowired
    AdministratorService administratorService;

    @PostMapping("/api/admin/personById")
    public Person getPersonById(@RequestParam String id) {
        return administratorService.getPersonById(id);
    }

    @GetMapping("/api/admin/allPeople")
    public List<Person> getAllPeople() {
        return administratorService.getAllPeople();
    }

    @PostMapping("/api/admin/addPerson")
    public void addPerson(@RequestBody Person person) {
        administratorService.savePerson(person);
    }

    @PostMapping("/api/admin/editPerson")
    public void editPerson(@RequestBody Person person) {
        administratorService.editPerson(person);
    }

    @PostMapping("/api/admin/deletePerson")
    public void deletePersonById(@RequestParam String id) {
        administratorService.deleteById(id);
    }
}
