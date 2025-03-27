package app.space;

import app.space.config.DBConfig;
import app.space.config.IOConfig;
import app.space.config.LoggingConfig;
import app.space.repo.ReservationRepo;
import app.space.repo.SpaceRepo;
import app.space.ui.AdminUI;
import app.space.ui.UI;
import app.space.ui.CustomerUI;
import app.space.service.AdminService;
import app.space.service.CustomerService;

public class SpaceApplication {
    private enum Role {
        ADMIN,
        CUSTOMER,
        NONE
    }

    private final AdminUI adminUI;
    private final CustomerUI customerUI;
    private final IOConfig ioConfig;
    private final LoggingConfig loggingConfig;
    private Role role = Role.NONE;

    public SpaceApplication() {
        ioConfig = IOConfig.getInstance();
        new DBConfig();
        loggingConfig = LoggingConfig.getInstance();
        System.out.println("--- Space App started ---");
        ReservationRepo reservationRepo = new ReservationRepo();
        SpaceRepo spaceRepo = new SpaceRepo();

        adminUI = new AdminUI(new AdminService(reservationRepo, spaceRepo), ioConfig.getScanner());
        customerUI = new CustomerUI(new CustomerService(reservationRepo, spaceRepo), ioConfig.getScanner());
    }

    public void run() {
        boolean isRunning = true;
        while(isRunning) {
            switch (role) {
                case ADMIN:
                    runAgent(adminUI);
                    break;
                case CUSTOMER:
                    runAgent(customerUI);
                    break;
                default:
                    printRules();
                    String input = ioConfig.getScanner().nextLine().toLowerCase();

                    if (input.equals("q")) {
                        isRunning = false;
                    } else {
                        storeRole(input);
                    }
                    break;
            }
        }
        ioConfig.getScanner().close();
        DBConfig.close();
        System.out.println("Bye!");
    }

    private void runAgent(UI controller) {
        loggingConfig.logInfo("Logged in " + role.name());
        if(!controller.run()) {
            loggingConfig.logInfo("Logged out of " + role.name());
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