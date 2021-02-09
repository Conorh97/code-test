package com.conorh.codetest.api.service;

import com.conorh.codetest.api.dto.PersonDto;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    PersonDto addPerson(Long id, String firstName, String lastName);
    Optional<PersonDto> editPerson(Long id, String firstName, String lastName);
    boolean deletePerson(Long id);
    List<PersonDto> getAll();
    Integer getPersonCount();
}
