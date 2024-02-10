package com.gmail.llemaxiss.spring.person.dto;

import com.gmail.llemaxiss.spring.common.dto.CommonDTO;

public class PersonDTO extends CommonDTO {
	private String name;
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}
