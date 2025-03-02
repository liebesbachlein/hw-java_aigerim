package controller;

import model.Space;
import service.AdminService;

import java.util.Arrays;
import java.util.Scanner;

public class AdminAgent extends Agent {
    private final AdminService adminService;
    private static final int MIN_SPACE_PRICE = 1;
    private static final int MAX_SPACE_PRICE = 100000;
    private static final int MAX_SPACE_NAME_LENGTH = 30;

    public AdminAgent(AdminService adminService, Scanner scanner) {
        super(adminService, scanner);
        this.adminService = adminService;
    }

    public boolean run() {
        printRules();
        String input = scanner.nextLine();
        switch (input) {
            case "0":
                logOut();
                return false;
            case "1":
                super.showSpaceAvailability();
                return true;
            case "2":
                createSpace();
                return true;
            case "3":
                removeSpace();
                return true;
            default:
                SpaceApplication.printInvalidCommand();
                return true;
        }
    }

    public void printRules() {
        System.out.println("\nAdmin Menu: "
                + "View all spaces — 1, "
                + "Add space — 2, "
                + "Remove space — 3, "
                + "Log out — 0");
    }

    private void createSpace() {
        System.out.println("Enter Name, Price, and Type of a new space (each on a new line).\n"
                + "Available space types: Open, Private, Room.");

        String name = scanner.nextLine();
        String price = scanner.nextLine();
        String type = scanner.nextLine();

        String errorMessage = validateSpaceInput(name, price, type);

        if(!errorMessage.isEmpty()) {
            System.out.print(errorMessage);
            return;
        }
        int parsedPrice = Integer.parseInt(price);

        Space.Type parsedType;

        switch (type.toLowerCase()) {
            case "open": {
                parsedType = Space.Type.OPEN;
                break;
            }
            case "private": {
                parsedType = Space.Type.PRIVATE;
                break;
            }
            case "room": {
                parsedType = Space.Type.ROOM;
                break;
            } default: {
                parsedType = Space.Type.OPEN;
            }
        }

        adminService.saveSpace(parsedType, name, parsedPrice);
        System.out.println("New space added!");
    }

    private String validateSpaceInput(String name, String price, String type) {
        StringBuilder errorMessage = new StringBuilder();

        if (name.length() == 0 || name.length() > MAX_SPACE_NAME_LENGTH) {
            errorMessage.append("(!) Name must be 1—30 symbols long\n");
        }

        if (!price.matches("[1-9][0-9]*")) {
            errorMessage.append("(!) Price must be a non-zero integer number\n");
        } else {
            try {
                int parsedPrice = Integer.parseInt(price);
                if (parsedPrice < MIN_SPACE_PRICE || parsedPrice > MAX_SPACE_PRICE) {
                    errorMessage.append("(!) Price must be in range ["
                            + MIN_SPACE_PRICE + ", "
                            + MAX_SPACE_PRICE + "].\n");
                }
            } catch (NumberFormatException e) {
                errorMessage.append("(!) Invalid price.\n");
            }
        }

        if (!Arrays.asList("open", "private", "room").contains(type.toLowerCase())) {
            errorMessage.append("(!) Invalid space type. Available space types: Open, Private, Room.\n");
        }

        return errorMessage.toString();
    }

    private void removeSpace() {
        System.out.println("Enter space ID to be removed:");
        String id = scanner.nextLine();
        int parsedId = -1;

        StringBuilder errorMessage = new StringBuilder();

        if (!id.matches("[0-9]+")) {
            errorMessage.append("(!) Space ID must consist of only numbers.\n");
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
            boolean res = adminService.deleteSpace(parsedId);
            if (res) {
                System.out.println("Space is removed!");
            } else {
                System.out.println("Space with such ID not found!");
            }
        }
    }

    protected void logOut() {
        System.out.println("Logged out of Admin!");
    }
}
