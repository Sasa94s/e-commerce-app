package com.udacity.ecommerce.unit;

import com.udacity.ecommerce.core.SecuredAbstractTests;
import com.udacity.ecommerce.model.persistence.Cart;
import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.persistence.UserOrder;
import com.udacity.ecommerce.model.persistence.repositories.OrderRepository;
import com.udacity.ecommerce.model.persistence.repositories.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;

import static com.udacity.ecommerce.utils.ModelUtils.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class OrderControllerTests extends SecuredAbstractTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OrderRepository orderRepository;

    @SneakyThrows
    @Test
    public void testOrderSubmitSuccess() {
        User user = getUser(ID);
        Cart cart = getCart(null);
        cart.setItems(getItems());
        user.setCart(cart);
        UserOrder order = UserOrder.createFromCart(user.getCart());
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(orderRepository.save(order)).thenReturn(order);

        mvc.perform(
                        post("/api/order/submit/{username}", user.getUsername())
                                .header("Authorization", authorizationValue))
                .andExpect(status().isOk());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(orderRepository, times(1)).save(order);
    }

    @SneakyThrows
    @Test
    public void testOrderSubmitUserNotFound() {
        User user = getUser(ID);
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        mvc.perform(
                        post("/api/order/submit/{username}", user.getUsername())
                                .header("Authorization", authorizationValue))
                .andExpect(status().isNotFound());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @SneakyThrows
    @Test
    public void testGetOrdersForUserSuccess() {
        User user = getUser(ID);
        Cart cart = getCart(null);
        cart.setItems(getItems());
        user.setCart(cart);
        UserOrder order = UserOrder.createFromCart(user.getCart());
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(orderRepository.findByUser(user)).thenReturn(Collections.singletonList(order));

        mvc.perform(
                        get("/api/order/history/{username}", user.getUsername())
                                .header("Authorization", authorizationValue))
                .andExpect(status().isOk());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(orderRepository, times(1)).findByUser(user);
    }

    @SneakyThrows
    @Test
    public void testGetOrdersForUserNotFound() {
        User user = getUser(ID);
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        mvc.perform(
                        get("/api/order/history/{username}", user.getUsername())
                                .header("Authorization", authorizationValue))
                .andExpect(status().isNotFound());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }
}
