package app.space.ui;

import app.space.service.CustomerService;
import app.space.util.Validator;

import java.sql.Date;
import java.sql.Time;
import java.util.Scanner;

public class CustomerUI extends UI {
    private final CustomerService customerService;

    public CustomerUI(CustomerService customerService, Scanner scanner) {
        super(customerService, scanner);
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
        return switch (scanner.nextLine()) {
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

        String name = scanner.nextLine();
        String spaceId = scanner.nextLine();
        String date = scanner.nextLine();
        String startHour = scanner.nextLine();
        String endHour = scanner.nextLine();

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
        String id = scanner.nextLine();
        String errorMessage = Validator.onId(id) ;
        if (errorMessage.isBlank()) {
            if (customerService.deleteReservation(Integer.parseInt(id))) System.out.println("Reservation is removed!");
            else System.out.println("(!) Reservation with such ID not found");
        } else {
            System.out.print(errorMessage);
        }
    }

    public void logOut() {
        System.out.println("Logged out of Customer!");
    }
}
