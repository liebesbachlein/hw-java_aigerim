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
    private boolean isSessionPersistable;

    public SpaceApplication() {
        System.out.println("--- Space App started ---");
        scanner = new Scanner(System.in);
        SpaceRepo spaceRepo = new SpaceRepo("space_storage");
        ReservationRepo reservationRepo = new ReservationRepo("reserv_storage");

        if (spaceRepo.init() && reservationRepo.init()) {
            isSessionPersistable = true;
        } else {
            isSessionPersistable = false;
            spaceRepo.disablePersistence();
            reservationRepo.disablePersistence();
            System.out.println("(!) Error occurred while configuring persistence. Your session will not be stored.");
        }

        adminAgent = new AdminController(new AdminService(reservationRepo, spaceRepo), scanner);
        customerAgent = new CustomerController(new CustomerService(reservationRepo, spaceRepo), scanner);
    }

    public void run() {
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
        if(!controller.run()) {
            role = Role.NONE;
        }
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