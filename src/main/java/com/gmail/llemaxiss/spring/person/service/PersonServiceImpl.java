package com.gmail.llemaxiss.spring.person.service;

import com.gmail.llemaxiss.spring.address.entity.Address;
import com.gmail.llemaxiss.spring.book.entity.Book;
import com.gmail.llemaxiss.spring.courseScore.enums.Course;
import com.gmail.llemaxiss.spring.passport.entity.Passport;
import com.gmail.llemaxiss.spring.passport.repos.PassportRepository;
import com.gmail.llemaxiss.spring.person.dto.PersonDTO;
import com.gmail.llemaxiss.spring.person.entity.Person;
import com.gmail.llemaxiss.spring.person.enums.PersonActiveStatus;
import com.gmail.llemaxiss.spring.person.repos.PersonRepository;
import com.gmail.llemaxiss.spring.personInfo.entity.PersonInfo;
import com.gmail.llemaxiss.spring.placeOfBirth.entity.PlaceOfBirth;
import com.gmail.llemaxiss.spring.placeOfBirth.repos.PlaceOfBirthRepository;
import com.gmail.llemaxiss.spring.some.entity.Some;
import com.gmail.llemaxiss.spring.street.entity.Street;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class PersonServiceImpl implements PersonService {
	private final PersonRepository personRepository;
	private final PlaceOfBirthRepository placeOfBirthRepository;
	private final PassportRepository passportRepository;

	public PersonServiceImpl(PersonRepository personRepository, PlaceOfBirthRepository placeOfBirthRepository,
													 PassportRepository passportRepository) {
		this.personRepository = personRepository;
		this.placeOfBirthRepository = placeOfBirthRepository;
		this.passportRepository = passportRepository;
	}

	@Override
	public List<Person> findAllEntity() {
		return personRepository.findAll();
	}

	@Override
	public UUID create(@NonNull PersonDTO personDTO) {
		Person person = new Person();

		// ---
		Passport passport = new Passport();
		passport.setNumber("123");

		passportRepository.save(passport);
		// ---

		// ---
		Street placeOfBirthAddressStreet = new Street();
		placeOfBirthAddressStreet.setName("Guanch-gou");
		placeOfBirthAddressStreet.setNumber(24);

		Address placeOfBirthAddress = new Address();
		placeOfBirthAddress.setCountry("China");
		placeOfBirthAddress.setCity("Phekin");
		placeOfBirthAddress.setStreet(placeOfBirthAddressStreet);

		PlaceOfBirth placeOfBirth = new PlaceOfBirth();
		placeOfBirth.setAddress(placeOfBirthAddress);

		if (placeOfBirth.getPerson() == null) {
			placeOfBirth.setPerson(new ArrayList<>());
		}
		placeOfBirth.getPerson().add(person); // ОБЯЗАТЕЛЬНО, если не стоит CASCADE_TYPE
		person.setPlaceOfBirth(placeOfBirth);

		placeOfBirthRepository.save(placeOfBirth);
		// ---
		
		// ---
		Book book1 = new Book();
		Book book2 = new Book();
		
		book1.setName("The lord of the rings");
		book2.setName("Hobbit");
		
		book1.setPersons(List.of(person));
		book2.setPersons(List.of(person));
		
		person.setBooks(List.of(book1, book2));
		// ---
		
		// ---
		PersonInfo personInfo = new PersonInfo();
		personInfo.setInfo("Some info");

		Address address1 = new Address();
		address1.setCountry("Russia");
		address1.setCity("Samara");

		Street street1 = new Street();
		street1.setName("Pushkina");
		street1.setNumber(100);

		address1.setStreet(street1);
		// ---

		// ---
		Address address2 = new Address();
		address2.setCountry("Germany");
		address2.setCity("Berlin");

		Street street2 = new Street();
		street2.setName("Kolotushkina");
		street2.setNumber(200);

		address2.setStreet(street2);
		// ---

		// ---
		Some some = new Some();
		some.setName("Some field");
		// ---

		person.setName(personDTO.getName());
		person.setAge(personDTO.getAge());
		person.setPersonActiveStatus(PersonActiveStatus.ACTIVE);

		person.setPassport(passport);
		person.setPersonInfo(personInfo);
		person.setAddress1(address1);
		person.setAddress2(address2);
		person.setSome(some);

		person.getPersonTypes()
			.addAll(Set.of("Lox", "Pidr"));

		person.getPsevdoNames()
			.addAll(List.of("Maxon", "Maksik", "Maksimilian"));

		person.getCourseScoreMap()
			.putAll(Map.of(
				Course.MATH, 5,
				Course.GEOGRAPHY, 5,
				Course.PHYSIC, 3,
				Course.RUSSIAN_LANG, 4
			));

		personRepository.save(person);

		return person.getId();
	}

	@Override
	public void update(@NonNull UUID id, @NonNull PersonDTO personDTO) {
		Person person = findById(id, false);

		person.setName(personDTO.getName());
		person.setAge(personDTO.getAge());

		personRepository.save(person);
	}

	@Override
	public void softDelete(@NonNull UUID id) {
		Person person = findById(id, false);

		person.setDeleteTs(new Date());
		person.setDeletedBy("admin");

		personRepository.save(person);
	}

	@Override
	public void hardDelete(@NonNull UUID id) {
		Person person = findById(id, false);

		personRepository.delete(person);
	}

	private Person findById(@NonNull UUID id, boolean allowNull) {
		Optional<Person> personOptional = personRepository.findById(id);

		if (personOptional.isPresent()) {
			return personOptional.get();
		} else if (allowNull) {
			return null;
		} else {
			throw new EntityNotFoundException(String.format("Person by id %s not found", id));
		}
	}
}
