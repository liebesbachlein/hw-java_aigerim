package controller;

import model.Reservation;
import model.Space;
import service.CustomerService;

import java.util.List;
import java.util.Scanner;

public class CustomerAgent extends Agent {
    private final CustomerService customerService;
    private static final int MAX_NAME_LENGTH = 30;

    public CustomerAgent(CustomerService customerService, Scanner scanner) {
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
        String input = scanner.nextLine();
        switch (input) {
            case "0":
                logOut();
                return false;
            case "1":
                showSpaceAvailability();
                return true;
            case "2":
                createReservation();
                return true;
            case "3":
                cancelReservation();
                return true;
            case "4":
                super.showReservations();
                return true;
            default:
                SpaceApplication.printInvalidCommand();
                return true;
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

        if(!errorMessage.isEmpty()) {
            System.out.print(errorMessage);
            return;
        }

        int parsedSpaceId = Integer.parseInt(spaceId);
        int parsedDate = Integer.parseInt(date);
        int parsedStartHour = Integer.parseInt(startHour);
        int parsedEndHour = Integer.parseInt(endHour);


        Space space = validateSpaceAvailability(parsedSpaceId, parsedDate, parsedStartHour, parsedEndHour);

        if(space == null) return;

        customerService.saveReservation(name, space, parsedDate, parsedStartHour, parsedEndHour);
        System.out.println("New reservation created!");
    }

    private String validateReservationInput(String name, String spaceId, String date, String startHour, String endHour) {
        StringBuilder errorMessage = new StringBuilder();

        if (name.length() == 0 || name.length() > MAX_NAME_LENGTH) {
            errorMessage.append("(!) Name must be 1—30 symbols long\n");
        }

        if (!spaceId.matches("[0-9]{0,3}")
        || !date.matches("([1-9]|[1-2][0-9]|3[0-1])")
        || !startHour.matches("([0-9]|1[0-9]|2[0-3])")
        || !endHour.matches("([0-9]|1[0-9]|2[0-3])") ) {
            errorMessage.append("(!) Invalid ID, Date, or/and Hour\n");
        } else {
            try {
                Integer.parseInt(spaceId);
            } catch (NumberFormatException e) {
                errorMessage.append("(!) Invalid ID.\n");
            }

            if (Integer.parseInt(startHour) >= Integer.parseInt(endHour))
                errorMessage.append("(!) End Hour must be bigger than Start Hour\n");
        }

        return errorMessage.toString();
    }

    private Space validateSpaceAvailability(int spaceId, int date, int startHour, int endHour) {
        Space space = customerService.findSpaceById(spaceId);

        if (space == null) {
            System.out.println("Space with such ID not found!");
            return null;
        }

        List<Reservation> reservations = customerService.findReservationBySpaceId(spaceId);
        for (Reservation reservation : reservations) {
            if (reservation.getDate() == date) {
                if ((startHour >= reservation.getStartHour() &&
                        startHour < reservation.getEndHour())
                        || (endHour > reservation.getStartHour() &&
                        endHour <= reservation.getEndHour())) {
                    System.out.println("(!) This space is reserved for this time slot.");
                    return null;
                }
            }
        }

        return space;
    }

    private void cancelReservation() {
        System.out.println("Enter reservation ID to be removed:");
        String id = scanner.nextLine();
        int parsedId = -1;

        StringBuilder errorMessage = new StringBuilder();

        if (!id.matches("[0-9]+")) {
            errorMessage.append("(!) Reservation ID must consist of only numbers.\n");
        } else {
            try {
                parsedId = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                errorMessage.append("(!) Invalid ID.\n");
            }
        }

        if (!errorMessage.isEmpty()) {
            System.out.print(errorMessage);
        } else {
            boolean res = customerService.deleteReservation(parsedId);
            if (res) {
                System.out.println("Reservation is removed!");
            } else {
                System.out.println("Reservation with such ID not found!");
            }
        }
    }

    public void logOut() {
        System.out.println("Logged out of Customer!");
    }
}
