package com.gmail.llemaxiss.spring.library.entity;

import com.gmail.llemaxiss.spring.book.entity.Book;
import com.gmail.llemaxiss.spring.common.entity.VersionedEntity;
import com.gmail.llemaxiss.spring.person.entity.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.UUID;

@Entity
/**
 * Помечаем сущность как неизменяемую
 */
@Immutable
@Table(name = "spring_library")
@Where(clause = "delete_ts IS NULL")
public class Library extends VersionedEntity {
	/**
	 * Указывает, что связь колонок, входящих в этот класс, должна быть уникальна
	 * Можно вынести внутренних класс в отдельную папку
	 */
	@EmbeddedId
	private Id id = new Id();
	
	@ManyToOne
	@JoinColumn(
		name = Id.PERSON_COL_NAME,
		/**
		 * Помечаем поле как неизменяемое (только для чтения)
		 */
		insertable = false,
		updatable = false
	)
	private Person person;
	
	@ManyToOne
	@JoinColumn(
		name = Id.BOOK_COL_NAME,
		/**
		 * Помечаем поле как неизменяемое (только для чтения)
		 */
		insertable = false,
		updatable = false
	)
	private Book book;
	
	public Library() {
	}
	
	public Library(Person person, Book book) {
		this.person = person;
		this.book = book;
		
		this.id.personId = person.getId();
		this.id.bookId = book.getId();
		
		/**
		 * Гарантирует ссылочную целостность, если отношения двунаправлены
		 */
		person.getLibraries().add(this);
		book.getLibraries().add(this);
	}
	
	public Id getId() {
		return id;
	}
	
	public void setId(Id id) {
		this.id = id;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	
	@Embeddable
	private static class Id implements Serializable {
		public static final String PERSON_COL_NAME = "person_id";
		public static final String BOOK_COL_NAME = "book_id";
		
		@Column(name = PERSON_COL_NAME)
		private UUID personId;
		
		@Column(name = BOOK_COL_NAME)
		private UUID bookId;
		
		public Id() {
		}
		
		public Id(UUID personId, UUID bookId) {
			this.personId = personId;
			this.bookId = bookId;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o != null && o instanceof Id) {
				Id that = (Id) o;
				
				return this.personId.equals(that.personId)
					&& this.bookId.equals(that.bookId);
			}
			
			return false;
		}
		
		@Override
		public int hashCode() {
			return personId.hashCode() + bookId.hashCode();
		}
	}
}
