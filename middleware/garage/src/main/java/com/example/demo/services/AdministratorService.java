package com.example.demo.services;


import com.example.demo.models.Person;
import com.example.demo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorService {

    @Autowired
    PersonRepository personRepository;

    public void savePerson(Person person) {
        personRepository.save(person);
    }

    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    public void deleteById(String id) {
        personRepository.deleteById(id);
    }

    public Person getPersonById(String id) {
        return personRepository.findById(id).get();
    }

    public void editPerson(Person updatedPerson) {
        personRepository.deleteById(updatedPerson.getId());
        personRepository.save(updatedPerson);
    }
}
