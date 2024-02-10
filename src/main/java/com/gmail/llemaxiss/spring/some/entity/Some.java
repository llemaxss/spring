package com.gmail.llemaxiss.spring.some.entity;

import java.io.Serializable;

public class Some implements Serializable {
	private String name;

	public Some() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
