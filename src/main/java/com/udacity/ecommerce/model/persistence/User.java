package com.udacity.ecommerce.model.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private long id;

    @Column(nullable = false, unique = true)
    @JsonProperty
    private String username;

    @Column(nullable = false)
    @JsonProperty
    private String password;

    @Column(nullable = false)
    @JsonProperty
    private String salt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    @JsonIgnore
    @ToString.Exclude
    private Cart cart;

}
