package com.example.demo.utils;

import com.example.demo.enums.RegistrationStatus;
import com.example.demo.enums.Role;
import com.example.demo.models.Person;
import com.example.demo.repositories.PersonRepository;


public class LoginRegistrationUtils {

    public String register(Person person, PersonRepository personRepository) {

        if (!isCorrectRole(person)) {
            if (isAvailableLogin(person, personRepository)) {
                if (isAvailableEmail(person, personRepository)) {
                    if (isAvailablePhoneNumber(person, personRepository)) {
                        personRepository.save(person);
                        return RegistrationStatus.CORRECT_REGISTRATION.toString();
                    } else
                        return RegistrationStatus.PHONE_NUMBER_UNAVAILABLE.toString();
                } else
                    return RegistrationStatus.EMAIL_UNAVAILABLE.toString();
            } else
                return RegistrationStatus.LOGIN_UNAVAILABLE.toString();
        } else
            return RegistrationStatus.WRONG_ROLE.toString();
    }

    public String register(Person person, PersonRepository personRepository, String id) {

        if (isCorrectRoleForWorker(person)) {
            if (isAvailableLogin(person, personRepository)) {
                if (isAvailableEmail(person, personRepository)) {
                    if (isAvailablePhoneNumber(person, personRepository)) {
                        person.setId(id);
                        personRepository.save(person);
                        return RegistrationStatus.CORRECT_REGISTRATION.toString();
                    } else
                        return RegistrationStatus.PHONE_NUMBER_UNAVAILABLE.toString();
                } else
                    return RegistrationStatus.EMAIL_UNAVAILABLE.toString();
            } else
                return RegistrationStatus.LOGIN_UNAVAILABLE.toString();
        } else
            return RegistrationStatus.WRONG_ROLE.toString();
    }

    private boolean isCorrectRole(Person person) {
        return person.getRole().equals(Role.WORKER) || person.getRole().equals(Role.ADMIN);
    }

    private boolean isCorrectRoleForWorker(Person person) {
        return person.getRole().equals(Role.ADMIN);
    }

    private boolean isAvailableLogin(Person person, PersonRepository personRepository) {
        return personRepository.findAll().stream().map(Person::getLogin).noneMatch(person.getLogin()::equals);
    }

    private boolean isAvailableEmail(Person person, PersonRepository personRepository) {
        return personRepository.findAll().stream().map(Person::getAddressEmail).noneMatch(person.getAddressEmail()::equals);
    }

    private boolean isAvailablePhoneNumber(Person person, PersonRepository personRepository) {
        return personRepository.findAll().stream().map(Person::getNumberPhone).noneMatch(person.getNumberPhone()::equals);
    }
}
