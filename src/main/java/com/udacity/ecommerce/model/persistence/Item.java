package com.udacity.ecommerce.model.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @Column(nullable = false)
    @JsonProperty
    private String name;

    @Column(nullable = false)
    @JsonProperty
    private BigDecimal price;

    @Column(nullable = false)
    @JsonProperty
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Item item = (Item) o;
        if (name == null || item.name == null) return false;
        if (price == null || item.price == null) return false;

        return Objects.equals(name, item.name) &&
                Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
