package com.gmail.llemaxiss.spring.person.service;

import com.gmail.llemaxiss.spring.person.dto.PersonDTO;
import com.gmail.llemaxiss.spring.person.entity.Person;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

public interface PersonService {
	List<Person> findAllEntity();

	UUID create(@NonNull PersonDTO personDTO);

	void update(@NonNull UUID id, @NonNull PersonDTO personDTO);

	void softDelete(@NonNull UUID id);

	void hardDelete(@NonNull UUID id);
}
