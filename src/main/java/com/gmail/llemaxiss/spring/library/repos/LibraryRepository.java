package com.gmail.llemaxiss.spring.library.repos;

import com.gmail.llemaxiss.spring.library.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Library.Id> {
}
