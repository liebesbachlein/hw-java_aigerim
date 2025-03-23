package app.space.ui;

import app.space.entity.Space;
import app.space.service.AdminService;
import app.space.util.Validator;

import java.util.Optional;
import java.util.Scanner;

public class AdminUI extends UI {
    private final AdminService adminService;

    public AdminUI(AdminService adminService, Scanner scanner) {
        super(adminService, scanner);
        this.adminService = adminService;
    }

    public boolean run() {
        printRules();
        return switch (scanner.nextLine()) {
            case "0" -> {
                logOut();
                yield false;
            }
            case "1" -> {
                super.printSpacesWithAvailability();
                yield true;
            }
            case "2" -> {
                super.printCalendar();
                yield true;
            }
            case "3" -> {
                createSpace();
                yield true;
            }
            case "4" -> {
                removeSpace();
                yield true;
            }
            default -> {
                System.out.println("Invalid command.");
                yield true;
            }
        };
    }

    public void printRules() {
        System.out.println("\nAdmin Menu: "
                + "View all spaces — 1, "
                + "[NEW!] View space occupation calendar — 2, "
                + "Add space — 3, "
                + "Remove space — 4, "
                + "Log out — 0");
    }

    private void createSpace() {
        System.out.println("Enter Name, Price, and Type of a new space (each on a new line).\n"
                + "Available space types: Open, Private, Room.");

        String name = scanner.nextLine();
        String price = scanner.nextLine();
        String type = scanner.nextLine();
        String errorMessage = validateSpaceObjectInput(name, price, type);

        if(errorMessage.isBlank()) {
            Optional<Space> newSpace = adminService.saveSpace(
                    switch(type.toLowerCase()) {
                        case "private" -> Space.Type.PRIVATE;
                        case "room" -> Space.Type.ROOM;
                        default -> Space.Type.OPEN;
                    },
                    name,
                    Integer.parseInt(price));
            if(newSpace.isPresent()) System.out.println("New space added!");
            else System.out.println("Space wasn't added!");
        } else {
            System.out.print(errorMessage);
        }
    }

    private String validateSpaceObjectInput(String name, String price, String type) {
        return Validator.onName(name) +
                System.lineSeparator() +
                Validator.onPrice(price) +
                System.lineSeparator() +
                Validator.onSpaceType(type);
    }

    private void removeSpace() {
        System.out.println("Enter space ID to be removed:");
        String id = scanner.nextLine();
        String errorMessage = Validator.onId(id) ;
        if (errorMessage.isBlank()) {
            if (adminService.deleteSpace(Integer.parseInt(id))) System.out.println("Space is removed!");
            else System.out.println("(!) Space with such ID not found.");
        } else {
            System.out.print(errorMessage);
        }
    }

    protected void logOut() {
        System.out.println("Logged out of Admin!");
    }
}
