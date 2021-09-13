package com.udacity.ecommerce.unit;

import com.udacity.ecommerce.core.SecuredAbstractTests;
import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.persistence.repositories.UserRepository;
import com.udacity.ecommerce.model.requests.CreateUserRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.net.URI;
import java.util.Optional;

import static com.udacity.ecommerce.utils.ModelUtils.getCreateUserRequest;
import static com.udacity.ecommerce.utils.ModelUtils.getUser;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class UserControllerTests extends SecuredAbstractTests {

    @Autowired
    private JacksonTester<CreateUserRequest> jsonCreateUser;

    @MockBean
    private UserRepository userRepository;

    @SneakyThrows
    @Test
    public void testSignupSuccess() {
        User user = getUser(ID);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
        when(userRepository.save(user)).thenReturn(user);

        CreateUserRequest request = getCreateUserRequest();
        mvc.perform(
                        post(new URI("/api/user/create"))
                                .content(jsonCreateUser.write(request).getJson())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").hasJsonPath());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @SneakyThrows
    @Test
    public void testSignupUsernameExists() {
        User user = getUser(ID);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        CreateUserRequest request = getCreateUserRequest();
        mvc.perform(
                        post(new URI("/api/user/create"))
                                .content(jsonCreateUser.write(request).getJson())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @SneakyThrows
    @Test
    public void testFindByIdSuccess() {
        User user = getUser(ID);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        mvc.perform(
                        get("/api/user/id/{id}", user.getId())
                                .header("Authorization", authorizationValue))
                .andExpect(status().isOk());

        verify(userRepository, times(1)).findById(user.getId());
    }

    @SneakyThrows
    @Test
    public void testFindByIdNotFound() {
        User user = getUser(ID);
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        mvc.perform(
                        get("/api/user/id/{id}", user.getId())
                                .header("Authorization", authorizationValue))
                .andExpect(status().isNotFound());

        verify(userRepository, times(1)).findById(user.getId());
    }

    @SneakyThrows
    @Test
    public void testFindByUsernameSuccess() {
        User user = getUser(ID);
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        mvc.perform(
                        get("/api/user/{username}", user.getUsername())
                                .header("Authorization", authorizationValue))
                .andExpect(status().isOk());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @SneakyThrows
    @Test
    public void testFindByUsernameNotFound() {
        User user = getUser(ID);
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        mvc.perform(
                        get("/api/user/{username}", user.getUsername())
                                .header("Authorization", authorizationValue))
                .andExpect(status().isNotFound());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

}
