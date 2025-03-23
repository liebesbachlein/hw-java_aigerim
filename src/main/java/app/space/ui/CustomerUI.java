package app.space.ui;

import app.space.service.CustomerService;
import app.space.util.Validator;

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
                + "[NEW!] View space occupation calendar — 2, "
                + "Make reservation — 3, "
                + "Cancel reservation — 4, "
                + "View my reservations — 5, "
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
                super.printCalendar();
                yield true;
            }
            case "3" -> {
                createReservation();
                yield true;
            }
            case "4" -> {
                cancelReservation();
                yield true;
            }
            case "5" -> {
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
            if (customerService.saveReservation(
                    name,
                    Integer.parseInt(spaceId),
                    Integer.parseInt(date),
                    Integer.parseInt(startHour),
                    Integer.parseInt(endHour))
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
