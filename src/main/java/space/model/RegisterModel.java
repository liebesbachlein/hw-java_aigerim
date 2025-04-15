package space.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterModel {
    @NotNull(message = "Required field")
    @Size(min = 1, max = 64, message = "Name must be of of length 1 - 64")
    String name;

    @Email(message = "Provide a valid email")
    @NotNull(message = "Required field")
    String email;

    @NotBlank(message = "Required field")
    @Size(min = 5, max = 32, message = "Password must be of of length 5 - 32")
    String password;
}
