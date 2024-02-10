package com.gmail.llemaxiss.spring.street.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StreetNumberConverter2 implements AttributeConverter<Integer, Integer> {
	@Override
	public Integer convertToDatabaseColumn(Integer attribute) {
		if (attribute != null) {
			return attribute + 10;
		}

		return attribute;
	}

	@Override
	public Integer convertToEntityAttribute(Integer dbData) {
		if (dbData != null) {
			return dbData - 10;
		}

		return dbData;
	}
}
