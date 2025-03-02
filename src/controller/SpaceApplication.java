package controller;

import model.Space;
import repo.ReservationRepo;
import repo.SpaceRepo;
import service.AdminService;
import service.CustomerService;

import java.util.*;

public class SpaceApplication {
    private enum Role {
        ADMIN,
        CUSTOMER,
        NONE
    }

    private final AdminAgent adminAgent;
    private final CustomerAgent customerAgent;
    private Role role = Role.NONE;
    private final Scanner scanner;

    public SpaceApplication() {
        scanner = new Scanner(System.in);
        SpaceRepo spaceRepo = new SpaceRepo();
        ReservationRepo reservationRepo = new ReservationRepo();

        spaceRepo.save(new Space(Space.Type.OPEN, "Fancy Conference", 12000));
        spaceRepo.save(new Space(Space.Type.PRIVATE, "Monaco Office", 7000));
        spaceRepo.save(new Space(Space.Type.ROOM, "Parisian Windows", 1000));

        adminAgent = new AdminAgent(new AdminService(reservationRepo, spaceRepo), scanner);
        customerAgent = new CustomerAgent(new CustomerService(reservationRepo, spaceRepo), scanner);
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

    private void runAgent(Agent agent) {
        if(!agent.run()) role = Role.NONE;
    }

    protected static void printInvalidCommand() {
        System.out.println("Invalid command.");
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
                printInvalidCommand();
                break;
        }
    }
}