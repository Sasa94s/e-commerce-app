package com.udacity.ecommerce.unit;

import com.udacity.ecommerce.core.SecuredAbstractTests;
import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.persistence.repositories.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.udacity.ecommerce.utils.ModelUtils.getUser;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthorizationTests extends SecuredAbstractTests {

    @MockBean
    private UserRepository userRepository;

    @SneakyThrows
    @Test
    public void testSecuredEndpointWithoutToken() {
        User user = getUser(ID);
        mvc.perform(
                        get("/api/user/{username}", user.getUsername()))
                .andExpect(status().isForbidden());
    }

    @SneakyThrows
    @Test
    public void testSecuredEndpointWithInvalidToken() {
        User user = getUser(ID);
        String invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        mvc.perform(
                        get("/api/user/{username}", user.getUsername())
                                .header("Authorization", String.format("Bearer %s", invalidToken)))
                .andExpect(status().isInternalServerError());
    }

    @SneakyThrows
    @Test
    public void testSecuredEndpointWithValidToken() {
        User user = getUser(ID);
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        mvc.perform(
                        get("/api/user/{username}", user.getUsername())
                                .header("Authorization", String.format("Bearer %s", token)))
                .andExpect(status().isOk());

        verify(userRepository).findByUsername(user.getUsername());
    }
}
