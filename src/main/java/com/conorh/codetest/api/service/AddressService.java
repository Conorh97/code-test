package com.conorh.codetest.api.service;

import com.conorh.codetest.api.dto.AddressDto;

import java.util.Optional;

public interface AddressService {

    AddressDto addAddress(Long personId, Long id, String street, String city, String state, String postalCode);
    Optional<AddressDto> editAddress(Long id, String street, String city, String state, String postalCode);
    boolean deleteAddress(Long id);
}
