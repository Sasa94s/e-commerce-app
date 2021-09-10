package com.udacity.ecommerce.model.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_order")
@Getter
@Setter
@ToString
public class UserOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	@Column
	private Long id;

	@ManyToMany(cascade = CascadeType.ALL)
	@JsonProperty
	@Column
	@ToString.Exclude
	private List<Item> items;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
	@JsonProperty
	@ToString.Exclude
	private User user;
	
	@JsonProperty
	@Column
	private BigDecimal total;

	public static UserOrder createFromCart(Cart cart) {
		UserOrder order = new UserOrder();
		order.setItems(new ArrayList<>(cart.getItems()));
		order.setTotal(cart.getTotal());
		order.setUser(cart.getUser());
		return order;
	}
	
}
