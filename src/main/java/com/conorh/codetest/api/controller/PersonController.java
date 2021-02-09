package com.conorh.codetest.api.controller;

import com.conorh.codetest.api.constants.Endpoints;
import com.conorh.codetest.api.dto.PersonDto;
import com.conorh.codetest.api.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = Endpoints.PERSON)
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<PersonDto> addPerson(
            @RequestParam(required = true) Long id,
            @RequestParam(required = true) String firstName,
            @RequestParam(required = true) String lastName) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.personService.addPerson(id, firstName, lastName));
    }

    @PostMapping("/{id}")
    public ResponseEntity<PersonDto> editPerson(
            @PathVariable(value = "id") Long id,
            @RequestParam(required = true) String firstName,
            @RequestParam(required = true) String lastName) {
        var result = this.personService.editPerson(id, firstName, lastName);
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePerson(@PathVariable(value = "id") Long id) {
        var result = this.personService.deletePerson(id);
        return result ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<PersonDto>> getAll() {
        return ResponseEntity.ok(this.personService.getAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(this.personService.getPersonCount());
    }
}
