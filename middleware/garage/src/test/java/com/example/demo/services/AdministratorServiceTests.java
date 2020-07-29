package com.example.demo.services;

import com.example.demo.enums.Role;
import com.example.demo.models.Person;
import com.example.demo.repositories.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AdministratorServiceTests {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private AdministratorService administratorService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @After
    public void tearDown()
    {
        Mockito.reset(personRepository);
    }

    @Test
    public void shouldSavePersonToRepositoryWhenPassedValidPerson() {
        // given
        Person p1 = new Person("jacek123", "haslo123", Role.ADMIN,
                "Jacek", "Placek", "+48123456789", "jacek12345@gmail.com");
        p1.setId("1");

        //when
        administratorService.savePerson(p1);
        // then
        Mockito.verify(personRepository, Mockito.times(1)).save(Mockito.any(Person.class));
        //  Assertions.assertThat(personRepository.findById("1")).isEqualTo(p1);
    }

    @Test
    public void shouldBeAnEmptyList() {
        // given
        Person mockPerson = new Person("jacek123", "haslo123", Role.ADMIN,
                "Jacek", "Placek", "+48123456789", "jacek12345@gmail.com");
        mockPerson.setId("1");
        Mockito.when(personRepository.findById("1")).thenReturn(Optional.of(mockPerson));
        //when
        administratorService.deleteById("1");
        // then
        Assertions.assertThat(administratorService.getAllPeople()).isEmpty();
    }

    @Test
    public void shouldGetPersonWithGivenId() {
        // given
        Person mockPerson = new Person("jacek123", "haslo123", Role.ADMIN,
                "Jacek", "Placek", "+48123456789", "jacek12345@gmail.com");
        mockPerson.setId("1");
        Mockito.when(personRepository.findById("1")).thenReturn(Optional.of(mockPerson));

        // when
        Person person = administratorService.getPersonById("1");

        // then
        Assertions.assertThat(person).isEqualTo(mockPerson);
    }

    @Test
    public void shouldBeAnEditedPerson() {
        // given
        Person mockPerson = new Person("jacek123", "haslo123", Role.ADMIN,
                "Jacek", "Placek", "+48123456789", "jacek12345@gmail.com");
        mockPerson.setId("1");
        Mockito.when(personRepository.findById("1")).thenReturn(Optional.of(mockPerson));

        //when
        Person p1 = new Person("jacek123", "haslo12345", Role.WORKER,
                "Jacek", "Placek", "+48123456789", "jacek12345@gmail.com");
        p1.setId("1");
        Mockito.when(personRepository.findById("1")).thenReturn(Optional.of(p1));

        Person p2 = administratorService.getPersonById("1");
        // then
        Assertions.assertThat(p2).isNotEqualTo(mockPerson);
    }

}
