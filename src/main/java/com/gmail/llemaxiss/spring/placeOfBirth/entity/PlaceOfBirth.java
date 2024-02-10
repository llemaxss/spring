package com.gmail.llemaxiss.spring.placeOfBirth.entity;

import com.gmail.llemaxiss.spring.address.entity.Address;
import com.gmail.llemaxiss.spring.common.entity.CommonEntity;
import com.gmail.llemaxiss.spring.person.entity.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Table(name = "spring_place_of_birth")
@Where(clause = "delete_ts IS NULL")
public class PlaceOfBirth extends CommonEntity {
	@Column(name = "address")
	private Address address;

	@OneToMany(
		fetch = FetchType.LAZY,
		mappedBy = "placeOfBirth"
	)
	private List<Person> person;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Person> getPerson() {
		return person;
	}

	public void setPerson(List<Person> person) {
		this.person = person;
	}
}
