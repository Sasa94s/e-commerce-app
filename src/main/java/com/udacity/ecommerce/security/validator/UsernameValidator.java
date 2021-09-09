package com.udacity.ecommerce.security.validator;

import com.udacity.ecommerce.model.persistence.repositories.UserRepository;
import com.udacity.ecommerce.security.validator.annotation.ValidUsername;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    private final UserRepository repository;

    @Override
    public void initialize(final ValidUsername arg0) {

    }

    @SneakyThrows
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.isEmpty()) return false;
        return repository.countByUsername(username) == 1;
    }

}
