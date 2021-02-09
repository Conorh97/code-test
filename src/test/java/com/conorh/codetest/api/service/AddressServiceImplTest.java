package com.conorh.codetest.api.service;

import com.conorh.codetest.api.entity.Address;
import com.conorh.codetest.api.entity.Person;
import com.conorh.codetest.api.repository.AddressRepository;
import com.conorh.codetest.api.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AddressServiceImplTest {

    private AddressService addressService;
    private AddressRepository addressRepository;
    private PersonRepository personRepository;

    private static final Long personId = 1L;
    private static final Long id = 2L;
    private static final String street = "TallStreet";
    private static final String city = "Dublin";
    private static final String state = "Leinster";
    private static final String postalCode = "AAA4444";

    @BeforeEach
    public void setup() {
        this.addressRepository = mock(AddressRepository.class);
        this.personRepository = mock(PersonRepository.class);
        when(this.personRepository.findById(anyLong())).thenReturn(Optional.of(Person.builder().id(personId).build()));
        this.addressService = new AddressServiceImpl(this.addressRepository, this.personRepository);
    }

    @Test
    public void addAddress_whenAddressInfoPassed_saveAddressAndReturn() {
        var argumentCaptor = ArgumentCaptor.forClass(Address.class);
        when(this.addressRepository.save(any(Address.class))).thenAnswer(i -> i.getArguments()[0]);

        var result = this.addressService.addAddress(personId, id, street, city, state, postalCode);

        verify(this.personRepository, times(1)).findById(anyLong());
        verify(this.addressRepository, times(1)).save(argumentCaptor.capture());
        assertEquals(personId, argumentCaptor.getValue().getPerson().getId());
        assertEquals(id, result.getId());
        assertEquals(street, result.getStreet());
        assertEquals(city, result.getCity());
        assertEquals(state, result.getState());
        assertEquals(postalCode, result.getPostalCode());
    }

    @Test
    public void editPerson_whenPersonDoesNotExist_returnEmptyOptional() {
        when(this.addressRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertTrue(this.addressService.editAddress(id, street, city, state, postalCode).isEmpty());
    }

    @Test
    public void editPerson_whenPersonExists_savePersonAndReturn() {
        when(this.addressRepository.findById(anyLong())).thenReturn(Optional.of(getAddress()));
        when(this.addressRepository.save(any(Address.class))).thenReturn(getAddress());

        var result = this.addressService.editAddress(id, street, city, state, postalCode);

        verify(this.addressRepository, times(1)).save(any(Address.class));
        assertTrue(result.isPresent());
    }

    @Test
    public void deletePerson_whenPersonDoesNotExist_returnFalse() {
        when(this.personRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertFalse(this.addressService.deleteAddress(id));
    }

    @Test
    public void deletePerson_whenPersonExists_deletePersonAndReturnTrue() {
        when(this.addressRepository.findById(anyLong())).thenReturn(Optional.of(getAddress()));

        var result = this.addressService.deleteAddress(id);

        verify(this.addressRepository, times(1)).deleteById(anyLong());
        assertTrue(result);
    }

    private Address getAddress() {
        return Address.builder()
                .id(id)
                .street(street)
                .city(city)
                .state(state)
                .postalCode(postalCode)
                .build();
    }
}