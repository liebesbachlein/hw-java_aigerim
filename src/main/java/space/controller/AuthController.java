package space.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import space.entity.User;
import space.model.LoginModel;
import space.model.RegisterModel;
import space.service.auth.AuthSessionService;
import space.util.*;

import javax.security.auth.login.CredentialException;

@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthSessionService authSessionService;

    @Autowired
    public AuthController(AuthSessionService authSessionService) {
        this.authSessionService = authSessionService;
    }

    @GetMapping("/login")
    public String login(
            @CookieValue(name = "SESSION", defaultValue = "") String sessionId,
            Model model,
            HttpServletResponse response) {
        model.addAttribute("loginInput", new LoginModel());

        return authorize(sessionId, "login");
    }

    private String authorize(String sessionIdId, String view) {
        try {
            User user = authSessionService.auth(sessionIdId);
            if (authSessionService.isAdmin(user)) return "redirect:/admin/dashboard";
            else return "redirect:/user/dashboard";
        } catch (InvalidSessionException e) {
            return view;
        }
    }

    @PostMapping("/login")
    public String processLoginForm(
            @Valid @ModelAttribute("loginInput") LoginModel input,
            BindingResult result,
            Model model,
            HttpServletResponse response) throws RepositoryException {
        if (result.hasErrors()) return "login";

        try {
            UserTokenPair userTokenPair = authSessionService.login(input.getEmail(), input.getPassword());
            Cookie cookie = new Cookie("SESSION", userTokenPair.getSession().getId());
            cookie.setPath("/");
            response.addCookie(cookie);
            if (authSessionService.isAdmin(userTokenPair.getUser())) return "redirect:/admin/dashboard";
            else return "redirect:/user/dashboard";
        } catch (CredentialException e) {
            model.addAttribute("error", "Email/password are incorrect");
            return "login";
        }
    }

    @GetMapping("/register")
    public String register(
            @CookieValue(name = "SESSION", defaultValue = "") String sessionId,
            Model model,
            HttpServletResponse response) {
        model.addAttribute("registerInput", new RegisterModel());

        return authorize(sessionId, "register");
    }

    @PostMapping("/register")
    public String processRegisterForm(
            @Valid @ModelAttribute("registerInput") RegisterModel input,
            BindingResult result,
            Model model,
            HttpServletResponse response) throws RepositoryException {
        if (result.hasErrors()) return "register";

        try {
            UserTokenPair userTokenPair = authSessionService.register(
                    input.getName(),
                    input.getEmail(),
                    input.getPassword());
            Cookie cookie = new Cookie("SESSION", userTokenPair.getSession().getId());
            cookie.setPath("/");
            response.addCookie(cookie);
            if (authSessionService.isAdmin(userTokenPair.getUser())) return "redirect:/admin/dashboard";
            else return "redirect:/user/dashboard";
        } catch (DuplicateUserException ex) {
            model.addAttribute("error", "User with email " + input.getEmail() + " already exists.");
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(
            @CookieValue(name = "SESSION", defaultValue = "") String sessionId,
            HttpServletResponse response) throws RepositoryException {
        authSessionService.logoutUser(sessionId);
        response.addCookie(new Cookie("SESSION",""));
        return "index";
    }

    @GetMapping("/logout-all-devices")
    public String logoutAllDevices(
            @CookieValue(name = "SESSION", defaultValue = "") String sessionId,
            HttpServletResponse response) throws RepositoryException {
        authSessionService.logoutUserFromAllDevices(sessionId);
        response.addCookie(new Cookie("SESSION",""));
        return "index";
    }
}
