package com.gmail.llemaxiss.spring.passport.entity;

import com.gmail.llemaxiss.spring.common.entity.CommonEntity;
import com.gmail.llemaxiss.spring.person.entity.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "spring_passport")
@Where(clause = "delete_ts IS NULL")
public class Passport extends CommonEntity {
	private String number;

	@OneToOne(mappedBy = "passport")
	private Person person;

	public Passport() {
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}
