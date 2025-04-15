package space.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import space.entity.User;
import space.model.*;
import space.service.CustomerService;
import space.service.auth.AuthSessionService;
import space.util.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Controller
@RequestMapping("/user/dashboard")
public class CustomerDashboardController {
    private final AuthSessionService authSessionService;
    private final CustomerService customerService;

    public CustomerDashboardController(AuthSessionService authSessionService, CustomerService customerService) {
        this.authSessionService = authSessionService;
        this.customerService = customerService;
    }

    @GetMapping
    public String showDashboard() {
        return "redirect:/user/dashboard/reservations";
    }

    @GetMapping("/reservations")
    public String showAllUserReservations(
            @CookieValue(name = "SESSION", defaultValue = "") String sessionId,
            Model model) {
        User user = authorize(sessionId);

        UserModel.injectUserDetails(model, user);
        ReservationModel.injectUserReservationList(model, customerService.getReservationsByUserId(user.getId()));

        return "user/dashboard/reservations";
    }

    @DeleteMapping("/reservation/{id}")
    public String cancelReservation(
            @PathVariable("id") int reservationId,
            @CookieValue(name = "SESSION", defaultValue = "") String sessionId) throws RepositoryException {
        User user = authorize(sessionId);

        if (customerService.isReservedByUser(reservationId, user.getId())) {
            customerService.cancelReservation(reservationId);
        } else {
            throw new UnauthorizedException();
        }

        return "redirect:/user/dashboard/reservations";
    }

    @GetMapping("/create-reservation")
    public String showSpaceReservations(
            @RequestParam(name = "id", defaultValue = "-1") int spaceId,
            @RequestParam(name = "date", defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date ,
            @CookieValue(name = "SESSION", defaultValue = "") String sessionId,
            Model model) {

        User user = authorize(sessionId);

        customerService.getSpace(spaceId).ifPresent(value -> SpaceModel.injectSpaceDetails(model, value));
        UserModel.injectUserDetails(model, user);
        SpaceModel.injectSpaceList(model, customerService.getAllSpaces());
        ReservationModel.injectSpaceReservationList(model, user, customerService.getReservationsBySpacesIdAndDate(spaceId, date));
        ReservationModel.injectDateError(model, date);

        return "user/dashboard/create-reservation";
    }

    @PostMapping("/create-reservation")
    @ResponseBody
    public void createReservation(
            @RequestBody ReservationModel reservationRequest,
            @CookieValue(name = "SESSION", defaultValue = "") String sessionId
    ) throws RepositoryException, ReservationTimeConflictException {
        User user = authorize(sessionId);

        customerService.createReservation(
                user,
                reservationRequest.getSpace(),
                reservationRequest.getDate(),
                reservationRequest.getHour());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ReservationTimeConflictException.class)
    public String handleReservationTimeConflict(ReservationTimeConflictException ex, Model model) {
        model.addAttribute("status", 409);
        model.addAttribute("description",
                "Oops, the space you tried to reserve is taken!");

        return "error";
    }

    private User authorize(String sessionId) {
        try {
            User user = authSessionService.auth(sessionId);
            if (authSessionService.isAdmin(user)) throw new UnauthorizedException();
            return user;
        } catch (InvalidSessionException e) {
            throw new UnauthorizedException();
        }
    }
}
