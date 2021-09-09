package com.udacity.ecommerce.model.requests;

import com.udacity.ecommerce.security.validator.annotation.PasswordValueMatch;
import com.udacity.ecommerce.security.validator.annotation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@PasswordValueMatch.List({
        @PasswordValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match!"
        )
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserRequest {

    @NotBlank(message = "username is mandatory")
    private String username;

    @NotBlank(message = "New password is mandatory")
    @ValidPassword
    private String password;

    @NotBlank(message = "Confirm Password is mandatory")
    @ValidPassword
    private String confirmPassword;

}
