package com.gmail.llemaxiss.spring.magazine.entity;

import com.gmail.llemaxiss.spring.common.entity.CommonEntity;
import com.gmail.llemaxiss.spring.library.entity.Library;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Table(name = "spring_magazine")
@Where(clause = "delete_ts IS NULL")
public class Magazine extends CommonEntity {
	@Column(name = "name")
	private String name;
	
	/**
	 * Кастомная реализация ManyToMany через связь OneToMany,
	 * где таблица со своими полями и составным ключом
	 */
	@OneToMany(
		fetch = FetchType.LAZY,
		mappedBy = "magazine"
	)
	private List<Library> libraries;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Library> getLibraries() {
		return libraries;
	}
	
	public void setLibraries(List<Library> libraries) {
		this.libraries = libraries;
	}
}
