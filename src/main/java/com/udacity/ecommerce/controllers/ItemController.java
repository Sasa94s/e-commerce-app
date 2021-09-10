package com.udacity.ecommerce.controllers;

import com.udacity.ecommerce.model.persistence.Item;
import com.udacity.ecommerce.model.persistence.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

	private final ItemRepository itemRepository;
	
	@GetMapping
	public ResponseEntity<List<Item>> getItems() {
        log.info("GetItems|Request");
        List<Item> items = itemRepository.findAll();
        log.info("GetItems|Response=[Count={}]", items.size());
        return ResponseEntity.ok(items);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        log.info("GetItemById|Request=(ID={})", id);
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (!optionalItem.isPresent()) {
            log.error("GetItemById|Response|Item #{} doesn't exist", id);
            return ResponseEntity.notFound().build();
        }

        Item item = optionalItem.get();
        log.info("GetItemById|Response={}", item);
        return ResponseEntity.ok(item);
    }
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
        log.info("GetItemsByName|Request=(Name={})", name);
        List<Item> items = itemRepository.findByName(name);
        if (items == null || items.isEmpty()) {
            log.error("GetItemsByName|Response|Item {} doesn't exist", name);
            return ResponseEntity.notFound().build();
        }

        log.info("GetItemsByName|Response|[Count={}]", items.size());
        return ResponseEntity.ok(items);

    }
	
}
