package com.udacity.ecommerce.controllers;

import com.udacity.ecommerce.model.persistence.Cart;
import com.udacity.ecommerce.model.persistence.Item;
import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.persistence.repositories.CartRepository;
import com.udacity.ecommerce.model.persistence.repositories.ItemRepository;
import com.udacity.ecommerce.model.persistence.repositories.UserRepository;
import com.udacity.ecommerce.model.requests.ModifyCartRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
	
	private final UserRepository userRepository;
	private final CartRepository cartRepository;
	private final ItemRepository itemRepository;

	@PostMapping("/addToCart")
	public ResponseEntity<Cart> addToCart(@RequestBody ModifyCartRequest request) {
		log.info("AddToCart|Request={}", request);
		User user = userRepository.findByUsername(request.getUsername());
		if (user == null) {
			log.error("AddToCart|Response|{} User doesn't exist", request.getUsername());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Optional<Item> item = itemRepository.findById(request.getItemId());
		if (!item.isPresent()) {
			log.error("AddToCart|Response|Item #{} doesn't exist", request.getItemId());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Cart cart = user.getCart();
		cart.setUser(user);
		IntStream.range(0, request.getQuantity())
				.forEach(i -> cart.addItem(item.get()));
		Cart savedCart = cartRepository.save(cart);
		log.info("AddToCart|Response={}", savedCart);
		return ResponseEntity.ok(savedCart);
	}

	@PostMapping("/removeFromCart")
	public ResponseEntity<Cart> removeFromCart(@RequestBody ModifyCartRequest request) {
		log.info("RemoveFromCart|Request={}", request);
		User user = userRepository.findByUsername(request.getUsername());
		if (user == null) {
			log.error("RemoveFromCart|Response|{} User doesn't exist", request.getUsername());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Optional<Item> item = itemRepository.findById(request.getItemId());
		if (!item.isPresent()) {
			log.error("RemoveFromCart|Response|Item #{} doesn't exist", request.getItemId());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Cart cart = user.getCart();
		IntStream.range(0, request.getQuantity())
				.forEach(i -> cart.removeItem(item.get()));
		Cart savedCart = cartRepository.save(cart);
		log.info("RemoveFromCart|Response={}", savedCart);
		return ResponseEntity.ok(savedCart);
	}
		
}
