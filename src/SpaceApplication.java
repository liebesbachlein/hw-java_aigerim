import controller.AdminController;
import controller.Controller;
import controller.CustomerController;
import model.Space;
import repo.ReservationRepo;
import repo.SpaceRepo;
import service.AdminService;
import service.CustomerService;
import util.DublicateIdException;

import java.util.*;

public class SpaceApplication {
    private enum Role {
        ADMIN,
        CUSTOMER,
        NONE
    }

    private final AdminController adminAgent;
    private final CustomerController customerAgent;
    private Role role = Role.NONE;
    private final Scanner scanner;

    public SpaceApplication() {
        scanner = new Scanner(System.in);
        SpaceRepo spaceRepo = new SpaceRepo();
        ReservationRepo reservationRepo = new ReservationRepo();


        try {
            spaceRepo.save(new Space(Space.Type.OPEN, "Fancy Conference", 12000));
            spaceRepo.save(new Space(Space.Type.PRIVATE, "Monaco Office", 7000));
            spaceRepo.save(new Space(Space.Type.ROOM, "Parisian Windows", 1000));
        } catch (DublicateIdException e) {
            System.out.println(e.getMessage());
        }

        adminAgent = new AdminController(new AdminService(reservationRepo, spaceRepo), scanner);
        customerAgent = new CustomerController(new CustomerService(reservationRepo, spaceRepo), scanner);
    }

    public void run() {
        System.out.println("Hello!");
        boolean isRunning = true;
        while(isRunning) {
            switch (role) {
                case Role.ADMIN:
                    runAgent(adminAgent);
                    break;
                case Role.CUSTOMER:
                    runAgent(customerAgent);
                    break;
                default:
                    printRules();
                    String input = scanner.nextLine().toLowerCase();

                    if (input.equals("q")) {
                        isRunning = false;
                    } else {
                        storeRole(input);
                    }
                    break;
            }
        }
        scanner.close();
        System.out.println("Bye!");
    }

    private void runAgent(Controller controller) {
        if(!controller.run()) role = Role.NONE;
    }

    private void printRules() {
        System.out.println("\nLog in to Admin — a, Log in to Customer — c, Quit — q");
    }

    private void storeRole(String input) {
        switch (input) {
            case "a":
                role = Role.ADMIN;
                break;
            case "c":
                role = Role.CUSTOMER;
                break;
            default:
                System.out.println("Invalid command.");
                break;
        }
    }
}