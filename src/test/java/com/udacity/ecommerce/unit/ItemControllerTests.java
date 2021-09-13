package com.udacity.ecommerce.unit;

import com.udacity.ecommerce.core.SecuredAbstractTests;
import com.udacity.ecommerce.model.persistence.repositories.ItemRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Optional;

import static com.udacity.ecommerce.utils.ModelUtils.getItem;
import static com.udacity.ecommerce.utils.ModelUtils.getItems;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ItemControllerTests extends SecuredAbstractTests {

    @MockBean
    private ItemRepository itemRepository;

    @SneakyThrows
    @Test
    public void testGetItems() {
        when(itemRepository.findAll()).thenReturn(getItems());

        mvc.perform(
                        get("/api/item")
                                .header("Authorization", authorizationValue))
                .andExpect(status().isOk());

        verify(itemRepository, times(1)).findAll();
    }

    @SneakyThrows
    @Test
    public void testGetItemByIdSuccess() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(getItem(ID)));

        mvc.perform(
                        get("/api/item/{id}", ID)
                                .header("Authorization", authorizationValue))
                .andExpect(status().isOk());

        verify(itemRepository, times(1)).findById(ID);
    }

    @SneakyThrows
    @Test
    public void testGetItemByIdNotFound() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        mvc.perform(
                        get("/api/item/{id}", ID)
                                .header("Authorization", authorizationValue))
                .andExpect(status().isNotFound());

        verify(itemRepository, times(1)).findById(ID);
    }

    @SneakyThrows
    @Test
    public void testGetItemsByNameSuccess() {
        String itemName = "Round";
        when(itemRepository.findByName(anyString())).thenReturn(Collections.singletonList(getItem(ID)));

        mvc.perform(
                        get("/api/item/name/{name}", itemName)
                                .header("Authorization", authorizationValue))
                .andExpect(status().isOk());

        verify(itemRepository, times(1)).findByName(itemName);
    }

    @SneakyThrows
    @Test
    public void testGetItemsByNameNotFound() {
        String itemName = "Round";
        when(itemRepository.findByName(anyString())).thenReturn(Collections.emptyList());

        mvc.perform(
                        get("/api/item/name/{name}", itemName)
                                .header("Authorization", authorizationValue))
                .andExpect(status().isNotFound());

        verify(itemRepository, times(1)).findByName(itemName);
    }
}
