package space.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import space.util.RepositoryException;
import space.util.UnauthorizedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RepositoryException.class)
    public String handleRepositoryExceptions(Exception ex, Model model) {
        model.addAttribute("status", 500);
        model.addAttribute("description",
                "Oops, that's a server error!");
        return "error";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public String handleUnauthorizedExceptions(Exception ex, Model model) {
        model.addAttribute("status", 401);
        model.addAttribute("description",
                "Oops, you're not authorized to access this page");
        return "error";
    }
}
