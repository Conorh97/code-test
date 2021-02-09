package com.conorh.codetest.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String postalCode;
}
