package com.udacity.ecommerce.unit;

import com.udacity.ecommerce.core.SecuredAbstractTests;
import com.udacity.ecommerce.model.persistence.Cart;
import com.udacity.ecommerce.model.persistence.Item;
import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.persistence.repositories.CartRepository;
import com.udacity.ecommerce.model.persistence.repositories.ItemRepository;
import com.udacity.ecommerce.model.persistence.repositories.UserRepository;
import com.udacity.ecommerce.model.requests.ModifyCartRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.udacity.ecommerce.utils.ModelUtils.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CartControllerTests extends SecuredAbstractTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private JacksonTester<ModifyCartRequest> jsonModifyCart;

    @SneakyThrows
    @Test
    public void testAddToCartSuccess() {
        User user = getUser(ID);
        final long itemId = 2L;
        Item item = getItem(itemId);
        Cart cart = getCart(ID);
        cart.setItems(Collections.singletonList(item));
        cart.setTotal(item.getPrice());
        cart.setUser(user);

        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(cartRepository.save(cart)).thenReturn(cart);

        ModifyCartRequest request = getModifyCartRequest(itemId);
        mvc.perform(
                        post("/api/cart/addToCart")
                                .header("Authorization", authorizationValue)
                                .content(jsonModifyCart.write(request).getJson())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userRepository, times(1)).findByUsername(getUser((Long) null).getUsername());
        verify(itemRepository, times(1)).findById(itemId);
        verify(cartRepository, times(1)).save(cart);
    }

    @SneakyThrows
    @Test
    public void testAddToCartUserNotFound() {
        final long itemId = 1L;

        when(userRepository.findByUsername(anyString())).thenReturn(null);

        ModifyCartRequest request = getModifyCartRequest(itemId);
        mvc.perform(
                        post("/api/cart/addToCart")
                                .header("Authorization", authorizationValue)
                                .content(jsonModifyCart.write(request).getJson())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userRepository, times(1)).findByUsername(getUser((Long) null).getUsername());
    }

    @SneakyThrows
    @Test
    public void testAddToCartItemNotFound() {
        User user = getUser(ID);
        final long itemId = 1L;
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        ModifyCartRequest request = getModifyCartRequest(itemId);
        mvc.perform(
                        post("/api/cart/addToCart")
                                .header("Authorization", authorizationValue)
                                .content(jsonModifyCart.write(request).getJson())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(itemRepository, times(1)).findById(itemId);
    }

    @SneakyThrows
    @Test
    public void testRemoveFromCartSuccess() {
        User user = getUser(ID);
        final long itemId = 1L;
        List<Item> items = getItems();
        Cart cart = getCart(ID);
        BigDecimal totalPrice = items.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setUser(user);
        cart.setItems(items);
        cart.setTotal(totalPrice);
        user.setCart(cart);

        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(items.get(0)));
        when(cartRepository.save(cart)).thenReturn(cart);

        ModifyCartRequest request = getModifyCartRequest(itemId);
        mvc.perform(
                        post("/api/cart/removeFromCart")
                                .header("Authorization", authorizationValue)
                                .content(jsonModifyCart.write(request).getJson())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(itemRepository, times(1)).findById(itemId);
        verify(cartRepository, times(1)).save(cart);
    }

    @SneakyThrows
    @Test
    public void testRemoveFromCartUserNotFound() {
        User user = getUser(ID);
        final long itemId = 1L;
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        ModifyCartRequest request = getModifyCartRequest(itemId);
        mvc.perform(
                        post("/api/cart/removeFromCart")
                                .header("Authorization", authorizationValue)
                                .content(jsonModifyCart.write(request).getJson())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @SneakyThrows
    @Test
    public void testRemoveFromCartItemNotFound() {
        User user = getUser(ID);
        final long itemId = 1L;
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        ModifyCartRequest request = getModifyCartRequest(itemId);
        mvc.perform(
                        post("/api/cart/removeFromCart")
                                .header("Authorization", authorizationValue)
                                .content(jsonModifyCart.write(request).getJson())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userRepository, times(1)).findByUsername(getUser((Long) null).getUsername());
        verify(itemRepository, times(1)).findById(itemId);
    }
}
