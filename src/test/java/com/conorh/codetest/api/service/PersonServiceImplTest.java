package com.conorh.codetest.api.service;

import com.conorh.codetest.api.entity.Person;
import com.conorh.codetest.api.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PersonServiceImplTest {

    private PersonServiceImpl personService;
    private PersonRepository personRepository;

    private static final Long personId = 1L;
    private static final String firstName = "Joe";
    private static final String lastName = "Bloggs";

    @BeforeEach
    public void setup() {
        this.personRepository = mock(PersonRepository.class);
        this.personService = new PersonServiceImpl(this.personRepository);
    }

    @Test
    public void addPerson_whenPersonDtoPassed_savePersonAndReturn() {
        when(this.personRepository.save(any(Person.class))).thenReturn(getPerson());

        var result = this.personService.addPerson(personId, firstName, lastName);

        verify(this.personRepository, times(1)).save(any(Person.class));
        assertEquals(personId, result.getId());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(0, result.getAddresses().size());
    }

    @Test
    public void editPerson_whenPersonDoesNotExist_returnEmptyOptional() {
        when(this.personRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertTrue(this.personService.editPerson(personId, firstName, lastName).isEmpty());
    }

    @Test
    public void editPerson_whenPersonExists_savePersonAndReturn() {
        when(this.personRepository.findById(anyLong())).thenReturn(Optional.of(getPerson()));
        when(this.personRepository.save(any(Person.class))).thenReturn(getPerson());

        var result = this.personService.editPerson(personId, firstName, lastName);

        verify(this.personRepository, times(1)).save(any(Person.class));
        assertTrue(result.isPresent());
    }

    @Test
    public void deletePerson_whenPersonDoesNotExist_returnFalse() {
        when(this.personRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertFalse(this.personService.deletePerson(personId));
    }

    @Test
    public void deletePerson_whenPersonExists_deletePersonAndReturnTrue() {
        when(this.personRepository.findById(anyLong())).thenReturn(Optional.of(getPerson()));

        var result = this.personService.deletePerson(personId);

        verify(this.personRepository, times(1)).deleteById(anyLong());
        assertTrue(result);
    }

    @Test
    public void getAll_whenPeoplePresent_returnListOfPeople() {
        when(this.personRepository.findAll()).thenReturn(List.of(getPerson(), getPerson()));

        var result = this.personService.getAll();

        verify(this.personRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertEquals(personId, result.get(0).getId());
        assertEquals(firstName, result.get(0).getFirstName());
        assertEquals(lastName, result.get(0).getLastName());
    }

    @Test
    public void getCount_whenPeoplePresent_returnNumberOfPeople() {
        when(this.personRepository.findAll()).thenReturn(List.of(getPerson(), getPerson()));

        var result = this.personService.getPersonCount();

        verify(this.personRepository, times(1)).findAll();
        assertEquals(2, result);
    }

    private Person getPerson() {
        return Person.builder()
                .id(personId)
                .firstName(firstName)
                .lastName(lastName)
                .addresses(List.of())
                .build();
    }

}