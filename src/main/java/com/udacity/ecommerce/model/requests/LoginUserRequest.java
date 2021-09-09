package com.udacity.ecommerce.model.requests;

import com.udacity.ecommerce.security.validator.annotation.ValidUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginUserRequest {

    @NotBlank(message = "username is mandatory")
    @ValidUsername
    private String username;

    @NotBlank(message = "New password is mandatory")
    private String password;

}
