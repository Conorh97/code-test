package com.conorh.codetest.api.service;

import com.conorh.codetest.api.dto.AddressDto;
import com.conorh.codetest.api.entity.Address;
import com.conorh.codetest.api.repository.AddressRepository;
import com.conorh.codetest.api.repository.PersonRepository;
import com.conorh.codetest.api.utils.AddressMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final PersonRepository personRepository;

    public AddressServiceImpl(AddressRepository addressRepository, PersonRepository personRepository) {
        this.addressRepository = addressRepository;
        this.personRepository = personRepository;
    }

    @Override
    public AddressDto addAddress(Long personId, Long id, String street, String city, String state, String postalCode) {
        var person = this.personRepository.findById(personId).get();
        var address = Address.builder()
                .id(id)
                .street(street)
                .city(city)
                .state(state)
                .postalCode(postalCode)
                .person(person)
                .build();
        return AddressMapper.toAddressDto(this.addressRepository.save(address));
    }

    @Override
    public Optional<AddressDto> editAddress(Long id, String street, String city, String state, String postalCode) {
        var existingAddress = this.addressRepository.findById(id);
        if (existingAddress.isEmpty()) {
            return Optional.empty();
        }
        var address = existingAddress.get();
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setPostalCode(postalCode);
        return Optional.of(AddressMapper.toAddressDto(this.addressRepository.save(address)));
    }

    @Override
    public boolean deleteAddress(Long id) {
        var existingAddress = this.addressRepository.findById(id);
        if (existingAddress.isEmpty()) {
            return false;
        }
        this.addressRepository.deleteById(id);
        return true;
    }
}
