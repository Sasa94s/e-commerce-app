package com.udacity.ecommerce.controllers;

import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.persistence.UserOrder;
import com.udacity.ecommerce.model.persistence.repositories.OrderRepository;
import com.udacity.ecommerce.model.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

	private final UserRepository userRepository;
	private final OrderRepository orderRepository;

	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrder> submit(@PathVariable String username) {
        log.info("Submit|Request=(Username={})", username);
		User user = userRepository.findByUsername(username);
		if(user == null) {
            log.error("Submit|Response|{} User doesn't exist", username);
			return ResponseEntity.notFound().build();
		}
		UserOrder order = UserOrder.createFromCart(user.getCart());
        orderRepository.save(order);
        log.info("Submit|Response={}", order);
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
        log.info("GetOrdersForUser|Request=(Username={})", username);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("GetOrdersForUser|Response|{} User doesn't exist", username);
            return ResponseEntity.notFound().build();
        }

        List<UserOrder> userOrders = orderRepository.findByUser(user);
        log.info("Submit|Response=[Count={}]", userOrders.size());
        return ResponseEntity.ok(userOrders);
    }
}
