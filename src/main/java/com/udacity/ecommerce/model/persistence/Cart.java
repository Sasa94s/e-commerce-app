package com.udacity.ecommerce.model.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@JsonProperty
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
		if (items == null) {
			items = new ArrayList<>();
		}
		items.remove(item);
		if (total == null) {
			total = new BigDecimal(0);
		}
		total = total.subtract(item.getPrice());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Cart cart = (Cart) o;
		if (user == null || cart.user == null) return false;

		return Objects.equals(user, cart.user);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
