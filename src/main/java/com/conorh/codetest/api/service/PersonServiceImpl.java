package com.conorh.codetest.api.service;

import com.conorh.codetest.api.dto.PersonDto;
import com.conorh.codetest.api.entity.Person;
import com.conorh.codetest.api.repository.PersonRepository;
import com.conorh.codetest.api.utils.PersonMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public PersonDto addPerson(Long id, String firstName, String lastName) {
        var person = Person.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        return PersonMapper.toPersonDto(this.personRepository.save(person));
    }

    @Override
    public Optional<PersonDto> editPerson(Long id, String firstName, String lastName) {
        var existingPerson = this.personRepository.findById(id);
        if (existingPerson.isEmpty()) {
            return Optional.empty();
        }
        var person = existingPerson.get();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return Optional.of(PersonMapper.toPersonDto(this.personRepository.save(person)));
    }

    @Override
    public boolean deletePerson(Long id) {
        var existingPerson = this.personRepository.findById(id);
        if (existingPerson.isEmpty()) {
            return false;
        }
        this.personRepository.deleteById(id);
        return true;
    }

    @Override
    public List<PersonDto> getAll() {
        var people = new ArrayList<PersonDto>();
        for (Person person : this.personRepository.findAll()) {
            people.add(PersonMapper.toPersonDto(person));
        }
        return people;
    }

    @Override
    public Integer getPersonCount() {
        var count = 0;
        for (Person person : this.personRepository.findAll()) {
            count++;
        }
        return count;
    }


}
