package space.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import space.entity.SpaceType;
import space.entity.User;
import space.model.SpaceModel;
import space.model.SpaceTypeModel;
import space.model.UserModel;
import space.service.AdminService;
import space.service.auth.AuthSessionService;
import space.util.InvalidSessionException;
import space.util.RepositoryException;
import space.util.UnauthorizedException;

@Controller
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {
    private final AuthSessionService authSessionService;
    private final AdminService adminService;

    @Autowired
    public AdminDashboardController(AuthSessionService authSessionService, AdminService adminService) {
        this.authSessionService = authSessionService;
        this.adminService = adminService;
    }

    @GetMapping
    public String dashboard() {
        return "redirect:/admin/dashboard/create-space";
    }

    @GetMapping("/create-space")
    public String showSpaceCreationForm(
            @CookieValue(name = "SESSION", defaultValue = "") String sessionId,
            Model model) {
        User user = authorize(sessionId);

        UserModel.injectUserDetails(model, user);
        SpaceModel.injectSpaceInput(model);
        SpaceTypeModel.injectSpaceTypeList(model, adminService.getAllSpaceTypes());

        return "admin/dashboard/create-space";
    }

    @PostMapping("/create-space")
    public String processCreateSpaceForm(
            @Valid @ModelAttribute("spaceInput") SpaceModel input,
            BindingResult result,
            @CookieValue(name = "SESSION", defaultValue = "") String sessionId,
            Model model) {
        User user = authorize(sessionId);

        UserModel.injectUserDetails(model, user);
        SpaceTypeModel.injectSpaceTypeList(model, adminService.getAllSpaceTypes());

        if (!result.hasErrors()) {
            try {
                adminService.saveSpace(
                        input.getName(),
                        input.getPrice(),
                        input.getTypeId());
                model.addAttribute("success", "Space was added");
            } catch (RepositoryException e) {
                model.addAttribute("error",
                        "Space with name \"" + input.getName() + "\" already exists");
            }
        }

        return "admin/dashboard/create-space";
    }

    @GetMapping("/spaces")
    public String showAllSpaces(
            @CookieValue(name = "SESSION", defaultValue = "") String sessionId,
            Model model) {
        User user = authorize(sessionId);

        UserModel.injectUserDetails(model, user);
        SpaceModel.injectSpaceList(model, adminService.getAllSpaces());

        return "admin/dashboard/spaces";
    }

    @DeleteMapping("/space/{id}")
    public String deleteSpace(
            @PathVariable("id") int spaceId,
            @CookieValue(name = "SESSION", defaultValue = "") String sessionId,
            Model model) throws RepositoryException {
        User user = authorize(sessionId);

        UserModel.injectUserDetails(model, user);
        adminService.deleteSpace(spaceId);

        return "redirect:/admin/dashboard/spaces";
    }

    private User authorize(String sessionId) {
        try {
            User user = authSessionService.auth(sessionId);
            if (!authSessionService.isAdmin(user)) throw new UnauthorizedException();
            return user;
        } catch (InvalidSessionException e) {
            throw new UnauthorizedException();
        }
    }
}
