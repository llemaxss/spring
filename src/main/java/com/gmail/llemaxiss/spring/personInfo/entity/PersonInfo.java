package com.gmail.llemaxiss.spring.personInfo.entity;

import java.io.Serializable;

public class PersonInfo implements Serializable {
	private String info;

	public PersonInfo() {
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
