package space.ui;

import org.springframework.stereotype.Component;
import space.service.CustomerService;
import space.util.Validator;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

import space.config.IO;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class CustomerUI extends UI {
    private final CustomerService customerService;

    @Autowired
    public CustomerUI(IO io, CustomerService customerService) {
        super(io, customerService);
        this.customerService = customerService;
    }


    public void printRules() {
        System.out.println("\nCustomer Menu: "
                + "View all spaces — 1, "
                + "Make reservation — 2, "
                + "Cancel reservation — 3, "
                + "View my reservations — 4, "
                + "Log out — 0");
    }

    public boolean run() {
        printRules();
        return switch (io.getScanner().nextLine()) {
            case "0" -> {
                logOut();
                yield false;
            }
            case "1" -> {
                printSpacesWithAvailability();
                yield true;
            }
            case "2" -> {
                createReservation();
                yield true;
            }
            case "3" -> {
                cancelReservation();
                yield true;
            }
            case "4" -> {
                super.printReservations();
                yield true;
            }
            default -> {
                System.out.println("Invalid command.");
                yield true;
            }
        };
    }

    private void createReservation() {
        System.out.println("Enter Owner Name, "
                + "ID of the Space, "
                + "Date Number, "
                + "Start Hour, "
                + "and End Hour (each on a new line).\n");

        String name = io.getScanner().nextLine();
        String spaceId = io.getScanner().nextLine();
        String date = io.getScanner().nextLine();
        String startHour = io.getScanner().nextLine();
        String endHour = io.getScanner().nextLine();

        String errorMessage = validateReservationInput(name, spaceId, date, startHour, endHour);

        if(errorMessage.isBlank()) {
            Date newDate = new Date(2025 - 1900, 7, Integer.parseInt(date));
            Time newStartHour = new Time(Integer.parseInt(startHour), 0, 0);
            Time newEndHour = new Time(Integer.parseInt(endHour), 0, 0);

            if (customerService.saveReservation(name,
                            Integer.parseInt(spaceId),
                            newDate,
                            newStartHour, newEndHour)
                    .isPresent()) {
                System.out.println("New reservation created!");
            } else {
                System.out.println("(!) Space with such ID not found or it was already reserved for this time slot. Or other error occurred");
            }
        } else {
            System.out.print(errorMessage);
        }
    }

    private String validateReservationInput(String name, String spaceId, String date, String startHour, String endHour) {
        return Validator.onName(name) +
                System.lineSeparator() +
                Validator.onId(spaceId) +
                System.lineSeparator() +
                Validator.onDateNumber(date) +
                System.lineSeparator() +
                Validator.onStartEndHours(startHour, endHour);
    }

    private void cancelReservation() {
        System.out.println("Enter reservation ID to be removed:");
        String id = io.getScanner().nextLine();
        String errorMessage = Validator.onId(id) ;
        if (errorMessage.isBlank()) {
            Optional<Boolean> res = customerService.deleteReservation(Integer.parseInt(id));
            if (res.isPresent() && res.get()) System.out.println("Reservation is removed!");
            else if (res.isPresent() && !res.get()) System.out.println("(!) Reservation with such ID not found");
        } else {
            System.out.print(errorMessage);
        }
    }

    public void logOut() {
        System.out.println("Logged out of Customer!");
    }
}
