package space.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.ui.Model;
import space.entity.User;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserModel {
    @NotNull(message = "Required field")
    @Size(min = 1, max = 64)
    String name;

    @Email
    @NotNull(message = "Required field")
    String email;

    @NotNull(message = "Required field")
    @Size(min = 1, max = 64)
    String password;

    public static UserModel mapUserModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setName(user.getName());
        userModel.setEmail(user.getEmail());
        userModel.setPassword(user.getPassword());
        return userModel;
    }

    public static void injectUserDetails(Model model, User user) {
        model.addAttribute("userDetails", mapUserModel(user));
    }
}
