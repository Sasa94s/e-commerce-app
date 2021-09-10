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
@Table(name = "cart")
@Getter
@Setter
@ToString
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	@Column
	private Long id;

	@ManyToMany
	@JsonProperty
	@Column
	@ToString.Exclude
	private List<Item> items;

	@OneToOne(mappedBy = "cart")
	@JsonProperty
	@ToString.Exclude
	private User user;
	
	@Column
	@JsonProperty
	private BigDecimal total;

	public void addItem(Item item) {
		if(items == null) {
			items = new ArrayList<>();
		}
		items.add(item);
		if(total == null) {
			total = new BigDecimal(0);
		}
		total = total.add(item.getPrice());
	}
	
	public void removeItem(Item item) {
		if(items == null) {
			items = new ArrayList<>();
		}
		items.remove(item);
		if(total == null) {
			total = new BigDecimal(0);
		}
		total = total.subtract(item.getPrice());
	}
}
