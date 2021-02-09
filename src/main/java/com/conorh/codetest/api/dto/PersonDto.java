package com.conorh.codetest.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonDto {
    private Long id;
    private String firstName;
    private String lastName;
    private List<AddressDto> addresses;
}
