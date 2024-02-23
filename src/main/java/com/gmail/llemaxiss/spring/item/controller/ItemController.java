package com.gmail.llemaxiss.spring.item.controller;

import com.gmail.llemaxiss.spring.item.service.ItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/item")
public class ItemController {
	private final ItemService itemService;
	
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}
	
	@PostMapping()
	public void init() {
		itemService.init();
	}
	
	@PutMapping()
	public void update() {
		itemService.update();
	}
}
