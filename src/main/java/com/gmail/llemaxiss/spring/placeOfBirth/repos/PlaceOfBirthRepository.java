package com.gmail.llemaxiss.spring.placeOfBirth.repos;

import com.gmail.llemaxiss.spring.placeOfBirth.entity.PlaceOfBirth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlaceOfBirthRepository extends JpaRepository<PlaceOfBirth, UUID> {
}
