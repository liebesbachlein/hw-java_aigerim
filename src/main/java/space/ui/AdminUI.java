package space.ui;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import space.config.IO;
import space.entity.Space;
import space.service.AdminService;
import space.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


@Component
public class AdminUI extends UI {
    private final AdminService adminService;

    @Autowired
    public AdminUI(IO io, AdminService adminService) {
        super(io, adminService);
        this.adminService = adminService;
    }

    public boolean run() {
        printRules();
        return switch (io.getScanner().nextLine()) {
            case "0" -> {
                logOut();
                yield false;
            }
            case "1" -> {
                super.printSpacesWithAvailability();
                yield true;
            }
            case "2" -> {
                createSpace();
                yield true;
            }
            case "3" -> {
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
                + "Add space — 2, "
                + "Remove space — 3, "
                + "Log out — 0");
    }

    @Transactional
    private void createSpace() {
        System.out.println("Enter Name and Price of a new space (each on a new line).");

        String name = io.getScanner().nextLine();
        String price = io.getScanner().nextLine();
        String errorMessage = validateSpaceObjectInput(name, price);

        if(errorMessage.isBlank()) {
            Optional<Space> newSpace = adminService.saveSpace(
                    name,
                    Integer.parseInt(price));
            if(newSpace.isPresent()) System.out.println("New space added!");
            else System.out.println("Space wasn't added!");
        } else {
            System.out.print(errorMessage);
        }
    }

    private String validateSpaceObjectInput(String name, String price) {
        return Validator.onName(name) +
                System.lineSeparator() +
                Validator.onPrice(price);
    }

    private void removeSpace() {
        System.out.println("Enter space ID to be removed:");
        String id = io.getScanner().nextLine();
        String errorMessage = Validator.onId(id) ;
        if (errorMessage.isBlank()) {
            Optional<Boolean> res = adminService.deleteSpace(Integer.parseInt(id));
            if (res.isPresent() && res.get()) System.out.println("Space is removed!");
            else if (res.isPresent() && !res.get()) System.out.println("(!) Space with such ID not found.");
        } else {
            System.out.print(errorMessage);
        }
    }

    protected void logOut() {
        System.out.println("Logged out of Admin!");
    }
}
