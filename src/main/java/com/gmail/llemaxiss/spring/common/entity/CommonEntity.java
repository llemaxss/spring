package com.gmail.llemaxiss.spring.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public class CommonEntity extends VersionedEntity implements Serializable {
	@Id
	@GeneratedValue(generator = "uuid2")
	@Column(name = "id")
	protected UUID id;

	@Override
	protected void prePersist() {
		if (id == null) {
			id = UUID.randomUUID();
		}
		
		super.prePersist();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
