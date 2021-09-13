package com.udacity.ecommerce.controllers;

import com.udacity.ecommerce.model.persistence.Cart;
import com.udacity.ecommerce.model.persistence.User;
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
		String username = createUserRequest.getUsername();
		User userFound = userRepository.findByUsername(username);
		if (userFound != null) {
			log.error("CreateUser|Response|{} User already exists", username);
			return ResponseEntity.badRequest().build();
		}

		String salt = BCrypt.gensalt();
		User user = User.builder()
				.username(username)
				.salt(salt)
				.password(BCrypt.hashpw(createUserRequest.getPassword(), salt))
				.cart(new Cart())
				.build();
		User savedUser = userRepository.save(user);
		log.info("CreateUser|Response={}", savedUser);
		return ResponseEntity.ok(savedUser);
	}
	
}
