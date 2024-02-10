package com.gmail.llemaxiss.spring.street.entity;

import com.gmail.llemaxiss.spring.street.entity.converter.StreetNumberConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converts;
import jakarta.persistence.Embeddable;

@Embeddable
public class Street {
	@Column(name = "name")
	private String name;

	@Column(name = "number")
	@Converts({
		@Convert(
			attributeName = "number",
			converter = StreetNumberConverter.class
		)
	})
	private Integer number;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
