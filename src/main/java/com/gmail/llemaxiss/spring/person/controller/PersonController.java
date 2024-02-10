package com.gmail.llemaxiss.spring.person.controller;

import com.gmail.llemaxiss.spring.common.dto.CommonIdDTO;
import com.gmail.llemaxiss.spring.person.dto.PersonDTO;
import com.gmail.llemaxiss.spring.person.entity.Person;
import com.gmail.llemaxiss.spring.person.service.PersonService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/person")
public class PersonController {
	private PersonService personService;

	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping
	public List<Person> findAllEntity() {
		return personService.findAllEntity();
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UUID create(@RequestBody PersonDTO personDTO) {
		return personService.create(personDTO);
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@PathVariable("id") UUID id, @RequestBody PersonDTO personDTO) {
		personService.update(id, personDTO);
	}

	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public void softDelete(@RequestBody CommonIdDTO commonIdDTO) {
		personService.softDelete(commonIdDTO.getId());
	}

	@DeleteMapping(path = "/hard", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void hardDelete(@RequestBody CommonIdDTO commonIdDTO) {
		personService.hardDelete(commonIdDTO.getId());
	}
}
