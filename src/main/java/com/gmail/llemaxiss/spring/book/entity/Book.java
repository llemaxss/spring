package com.gmail.llemaxiss.spring.book.entity;

import com.gmail.llemaxiss.spring.common.entity.CommonEntity;
import com.gmail.llemaxiss.spring.library.entity.Library;
import com.gmail.llemaxiss.spring.person.entity.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Table(name = "spring_book")
@Where(clause = "delete_ts IS NULL")
public class Book extends CommonEntity {
	@Column(name = "name")
	private String name;
	
	@ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
	private List<Person> persons;
	
	/**
	 * Кастомная реализация ManyToMany через связь OneToMany,
	 * где таблица со своими полями и составным ключом
	 */
	@OneToMany(
		fetch = FetchType.LAZY,
		mappedBy = "book"
	)
	private List<Library> libraries;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Person> getPersons() {
		return persons;
	}
	
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	
	public List<Library> getLibraries() {
		return libraries;
	}
	
	public void setLibraries(List<Library> libraries) {
		this.libraries = libraries;
	}
}
