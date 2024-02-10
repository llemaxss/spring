package com.gmail.llemaxiss.spring.address.entity;

import com.gmail.llemaxiss.spring.street.entity.Street;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class Address {
	@Column(name = "country")
	private String country;

	@Column(name = "city")
	private String city;

	/**
	 * Будет null, если все поля Street = null
	 */
	/**
	 * и/или указать @Embedded, и/или @Embeddable в Address
	 */
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "name", column = @Column(name = "street_name")),
		@AttributeOverride(name = "number", column = @Column(name = "street_number"))
	})
	private Street street;

	public Address() {
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}
}
