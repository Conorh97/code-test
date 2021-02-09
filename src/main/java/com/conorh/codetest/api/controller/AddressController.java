package com.conorh.codetest.api.controller;

import com.conorh.codetest.api.constants.Endpoints;
import com.conorh.codetest.api.dto.AddressDto;
import com.conorh.codetest.api.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = Endpoints.ADDRESS)
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping()
    public ResponseEntity<AddressDto> addAddress(
            @RequestParam(required = true)Long personId,
            @RequestParam(required = true) Long id,
            @RequestParam(required = true) String street,
            @RequestParam(required = true) String city,
            @RequestParam(required = true) String state,
            @RequestParam(required = true) String postalCode) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.addressService.addAddress(personId, id, street, city, state, postalCode));
    }

    @PostMapping("/{id}")
    public ResponseEntity<AddressDto> editAddress(
            @PathVariable(value = "id") Long id,
            @RequestParam(required = true) String street,
            @RequestParam(required = true) String city,
            @RequestParam(required = true) String state,
            @RequestParam(required = true) String postalCode) {
        var result = this.addressService.editAddress(id, street, city, state, postalCode);
        return result.isPresent() ? ResponseEntity.ok(result.get()) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAddress(@PathVariable(value = "id") Long id) {
        var result = this.addressService.deleteAddress(id);
        return result ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

}
