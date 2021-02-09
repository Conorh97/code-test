package com.conorh.codetest.api.utils;

import com.conorh.codetest.api.dto.PersonDto;
import com.conorh.codetest.api.entity.Person;

import java.util.stream.Collectors;

public class PersonMapper {

    public static PersonDto toPersonDto(Person person) {
        return PersonDto.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .addresses(person.getAddresses().stream().map(AddressMapper::toAddressDto).collect(Collectors.toList()))
                .build();
    }
}
