package com.gmail.llemaxiss.spring.item.service;

import com.gmail.llemaxiss.spring.item.entity.Item;
import com.gmail.llemaxiss.spring.item.repos.ItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class ItemServiceImpl implements ItemService {
	@PersistenceContext
	private EntityManager em;
	private final ItemRepository itemRepository;
	
	public ItemServiceImpl(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	
	@Override
	@Transactional
	public void init() {
		Item item = new Item();
		
		item.setName("AAA");
		
		itemRepository.save(item);
	}
	
	@Override
	@Transactional
	public void update() {
		Optional<Item> itemOptional = itemRepository.findById(UUID.fromString("97b203ca-efef-4a62-8cb4-f9cf3ffc2298"));
		
		if (itemOptional.isPresent()) {
			Item item = itemOptional.get();
			
			item.setName("ССС");
			
			/**
			 * Поле version будет обновлено автоматически,
			 * причем БД првоерит изменилось ли само поле, перед текущим изменением
			 *
			 * (точнее хибернейт попытается сделать так:
			 * UPDATE ... SET ... WHERE version = ...
			 * Если результат операции > 0, значит произошли изменения,
			 * если нет, значит версия поменялась ранее
			 * )
			 *
			 * Если не изменилось - значит все ок
			 * Если изменилось, то текущий объект считается устаревшим,
			 * и метод save откатится
			 */
			itemRepository.save(item);
		}
	}
}
