package com.gmail.llemaxiss.spring.magazine.repos;

import com.gmail.llemaxiss.spring.magazine.entity.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, UUID> {
}
