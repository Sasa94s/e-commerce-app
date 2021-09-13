package com.udacity.ecommerce.utils;

import com.udacity.ecommerce.model.persistence.Cart;
import com.udacity.ecommerce.model.persistence.Item;
import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.requests.CreateUserRequest;
import com.udacity.ecommerce.model.requests.LoginUserRequest;
import com.udacity.ecommerce.model.requests.ModifyCartRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelUtils {

    public static UserDetails getUser(User user) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }

    public static User getUser(Long id) {
        return User.builder()
                .id(id)
                .username("Sasa94s")
                .password("$2a$10$j.dv523AuPF0nZQLBEU1LeDFzccOmfu/fisrHVZax7GaJ01e2ENbe")
                .salt("$2a$10$j.dv523AuPF0nZQLBEU1Le")
                .cart(new Cart())
                .build();
    }

    public static CreateUserRequest getCreateUserRequest() {
        return CreateUserRequest.builder()
                .username("Sasa94s")
                .password("passw0rD!")
                .confirmPassword("passw0rD!")
                .build();
    }

    public static LoginUserRequest getLoginUserRequest() {
        return LoginUserRequest.builder()
                .username("Sasa94s")
                .password("passw0rD!")
                .build();
    }

    public static List<Item> getItems() {
        return new ArrayList<Item>() {{
            add(getItem(1L));
            add(getItem(2L));
        }};
    }

    public static Item getItem(Long id) {
        if (id == null) id = 1L;
        String label = (id % 2 == 0 ? "Square" : "Round");
        BigDecimal price = BigDecimal.valueOf((id % 2 == 0 ? 3.99 : 1.99));

        return Item.builder()
                .id(id)
                .name(label + " Widget")
                .description("A widget that is " + label.toLowerCase())
                .price(price)
                .build();
    }

    public static ModifyCartRequest getModifyCartRequest(long id) {
        return ModifyCartRequest.builder()
                .username("Sasa94s")
                .itemId(id)
                .quantity(1)
                .build();
    }

    public static Cart getCart(Long id) {
        return Cart.builder()
                .id(id)
                .user(getUser((Long) null))
                .items(new ArrayList<>())
                .total(BigDecimal.valueOf(0))
                .build();
    }

}
