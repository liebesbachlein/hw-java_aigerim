package space.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginModel {
    @Email(message = "Provide a valid email")
    @NotBlank(message = "Required field")
    private String email;

    @NotBlank(message = "Required field")
    @Size(min = 5, max = 32, message = "Password must be of of length 5 - 32")
    private String password;
}
