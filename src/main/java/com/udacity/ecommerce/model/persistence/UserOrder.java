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
@Table(name = "user_order")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserOrder userOrder = (UserOrder) o;
        if (user == null || userOrder.user == null) return false;

        return Objects.equals(id, userOrder.id) &&
                Objects.equals(user, userOrder.user);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
