package com.gmail.llemaxiss.spring.item.repos;

import com.gmail.llemaxiss.spring.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
}
