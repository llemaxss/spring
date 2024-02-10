package com.gmail.llemaxiss.spring.passport.repos;

import com.gmail.llemaxiss.spring.passport.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PassportRepository extends JpaRepository<Passport, UUID> {
}
