package com.gmail.llemaxiss.spring.library.entity;

import com.gmail.llemaxiss.spring.common.entity.VersionedEntity;
import com.gmail.llemaxiss.spring.magazine.entity.Magazine;
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
import java.util.ArrayList;
import java.util.UUID;

/**
 * Все, что будет помечено как nullable = false - будет считаться частью id,
 * поэтому если нужно иметь еще одно не нулевое поле, но которое НЕ явялеется частью id,
 * то можно пометить спринговой аннотацией @NotNull.
 * Но если нужно и в БД, то надо вручную создать ограничение, а не через хиберенейт
 */
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
		name = Id.MAGAZINE_COL_NAME,
		/**
		 * Помечаем поле как неизменяемое (только для чтения)
		 */
		insertable = false,
		updatable = false
	)
	private Magazine magazine;
	
	public Library() {
	}
	
	public Library(Person person, Magazine magazine) {
		this.person = person;
		this.magazine = magazine;
		
		this.id.personId = person.getId();
		this.id.magazineId = magazine.getId();
		
		/**
		 * Гарантирует ссылочную целостность, если отношения двунаправлены
		 */
		
		if (person.getLibraries() == null) {
			person.setLibraries(new ArrayList<>());
		}
		person.getLibraries().add(this);
		
		if (magazine.getLibraries() == null) {
			magazine.setLibraries(new ArrayList<>());
		}
		magazine.getLibraries().add(this);
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
	
	public Magazine getMagazine() {
		return magazine;
	}
	
	public void setMagazine(Magazine magazine) {
		this.magazine = magazine;
	}
	
	@Embeddable
	public static class Id implements Serializable {
		public static final String PERSON_COL_NAME = "person_id";
		public static final String MAGAZINE_COL_NAME = "magazine_id";
		
		@Column(name = PERSON_COL_NAME)
		private UUID personId;
		
		@Column(name = MAGAZINE_COL_NAME)
		private UUID magazineId;
		
		public Id() {
		}
		
		public Id(UUID personId, UUID magazineId) {
			this.personId = personId;
			this.magazineId = magazineId;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o != null && o instanceof Id) {
				Id that = (Id) o;
				
				return this.personId.equals(that.personId)
					&& this.magazineId.equals(that.magazineId);
			}
			
			return false;
		}
		
		@Override
		public int hashCode() {
			return personId.hashCode() + magazineId.hashCode();
		}
	}
}
