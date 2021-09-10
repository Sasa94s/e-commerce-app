package com.udacity.ecommerce.model.requests;

import com.udacity.ecommerce.security.validator.annotation.PasswordValueMatch;
import com.udacity.ecommerce.security.validator.annotation.ValidPassword;
import lombok.*;

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
@ToString
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
