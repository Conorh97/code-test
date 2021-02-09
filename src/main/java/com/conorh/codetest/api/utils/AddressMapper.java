package com.conorh.codetest.api.utils;

import com.conorh.codetest.api.dto.AddressDto;
import com.conorh.codetest.api.entity.Address;

public class AddressMapper {

    public static AddressDto toAddressDto(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .build();
    }
}
