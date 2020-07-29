package com.example.demo.utils;

import com.example.demo.models.Person;
import com.example.demo.repositories.PersonRepository;
import com.example.demo.services.ClientService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class Security {
    private static final String SECRET_KEY = "SECRET_KEY";

    public static String getSecretKey() {
        return SECRET_KEY;
    }


    public ResponseEntity getToken(Person person, PersonRepository personRepository) {
        return generateToken(person, personRepository);
    }

    private Person getPersonByLogin(Person person, PersonRepository personRepository) {

        return personRepository.findAll().stream().filter(p -> p.getLogin().equals(person.getLogin())).findFirst().get();
    }

    private boolean isCorrectPassword(Person person, PersonRepository personRepository) {
        Person personWithCheckedLogin = getPersonByLogin(person, personRepository);

        return personWithCheckedLogin.getLogin().equals(person.getLogin()) && personWithCheckedLogin.getPassword().equals(person.getPassword());
    }

    private ResponseEntity generateToken(Person person, PersonRepository personRepository) {

        try {
            if (isCorrectPassword(person, personRepository)) {
                Person checkedPerson = getPersonByLogin(person, personRepository);
                long currentTimeMillis = System.currentTimeMillis();
                String token = Jwts.builder()
                        .claim("login", checkedPerson.getLogin())
                        .claim("password", checkedPerson.getPassword())
                        .claim("role", checkedPerson.getRole())
                        .claim("id", ClientService.getIdByLoginAndPassword(person, personRepository))
                        .setIssuedAt(new Date(currentTimeMillis))
                        .setExpiration(new Date(currentTimeMillis + 250000))
                        .signWith(SignatureAlgorithm.HS512, Security.getSecretKey())
                        .compact();

                JSONObject jsonpObject = new JSONObject();

                jsonpObject.put("token", token);


                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(jsonpObject.toString());
            } else
                return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
