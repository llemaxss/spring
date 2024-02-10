package com.gmail.llemaxiss.spring.common.dto;

import java.io.Serializable;
import java.util.UUID;

public class CommonIdDTO implements Serializable {
	private UUID id;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
