package com.udacity.ecommerce.core;

import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.security.JWTService;
import com.udacity.ecommerce.utils.ModelUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class SecuredAbstractTests extends AbstractTests {

    @Autowired
    protected JWTService jwtService;

    protected static String token;
    protected static String authorizationValue;

    @BeforeAll
    public void setUp() {
        User user = ModelUtils.getUser(ID);
        token = jwtService.createToken(user.getUsername());
        authorizationValue = String.format("Bearer %s", token);
    }
}
