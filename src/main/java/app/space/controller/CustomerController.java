package main.java.app.space.controller;

import main.java.app.space.service.CustomerService;
import main.java.app.space.util.Validator;

import java.util.Scanner;

public class CustomerController extends Controller {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService, Scanner scanner) {
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
        switch (scanner.nextLine()) {
            case "0": {
                logOut();
                return false;
            }
            case "1": {
                printSpacesWithAvailability();
                return true;
            }
            case "2": {
                createReservation();
                return true;
            }
            case "3": {
                cancelReservation();
                return true;
            }
            case "4": {
                super.printReservations();
                return true;
            }
            default: {
                System.out.println("Invalid command.");
                return true;
            }
        }
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
            int parsedSpaceId = Integer.parseInt(spaceId);
            int parsedDate = Integer.parseInt(date);
            int parsedStartHour = Integer.parseInt(startHour);
            int parsedEndHour = Integer.parseInt(endHour);

            if (customerService.saveReservation
                    (name, parsedSpaceId, parsedDate, parsedStartHour, parsedEndHour) != null) {
                System.out.println("New reservation created!");
            } else {
                System.out.println("(!) Space with such ID not found or it was already reserved for this time slot.");
            }

        } else {
            System.out.print(errorMessage);
        }
    }

    private String validateReservationInput(String name, String spaceId, String date, String startHour, String endHour) {
        StringBuilder errorMessage = new StringBuilder();

        return errorMessage
                .append(Validator.onName(name))
                .append(System.getProperty("line.separator"))
                .append(Validator.onId(spaceId))
                .append(System.getProperty("line.separator"))
                .append(Validator.onStartEndHours(startHour, endHour)).toString();
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
