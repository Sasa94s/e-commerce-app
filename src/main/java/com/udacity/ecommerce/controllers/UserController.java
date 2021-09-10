package com.udacity.ecommerce.controllers;

import com.udacity.ecommerce.model.persistence.Cart;
import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.persistence.repositories.CartRepository;
import com.udacity.ecommerce.model.persistence.repositories.UserRepository;
import com.udacity.ecommerce.model.requests.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserRepository userRepository;
	private final CartRepository cartRepository;

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		log.info("FindById|Request=(ID={})", id);
		Optional<User> optionalUser = userRepository.findById(id);
		if (!optionalUser.isPresent()) {
			log.error("FindById|Response|User #{} doesn't exist", id);
			return ResponseEntity.notFound().build();
		}
		User user = optionalUser.get();
		log.info("FindById|Response={}", user);
		return ResponseEntity.ok(user);
	}

	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) {
		log.info("FindByUsername|Request=(Username={})", username);
		User user = userRepository.findByUsername(username);
		if (user == null) {
			log.error("FindByUserName|Response|{} User doesn't exist", username);
			return ResponseEntity.notFound().build();
		}
		log.info("FindByUserName|Response={}", user);
		return ResponseEntity.ok(user);
	}

	@PostMapping("/create")
	public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
		log.info("CreateUser|Request={}", createUserRequest);
		User user = new User();
		user.setUsername(createUserRequest.getUsername());
		String salt = BCrypt.gensalt();
		String encryptedPassword = BCrypt.hashpw(createUserRequest.getPassword(), salt);
		user.setSalt(salt);
		user.setPassword(encryptedPassword);
		Cart cart = new Cart();
		cartRepository.save(cart);
		user.setCart(cart);
		userRepository.save(user);
		log.info("CreateUser|Response={}", user);
		return ResponseEntity.ok(user);
	}
	
}
