package space.ui;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import space.config.IO;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class App {
    private enum Role {
        ADMIN,
        CUSTOMER,
        NONE
    }

    private final AdminUI adminUI;
    private final CustomerUI customerUI;
    private final IO io;
    private Role role = Role.NONE;

    @Autowired
    public App(AdminUI adminUI, CustomerUI customerUI, IO io) {
        this.adminUI = adminUI;
        this.customerUI = customerUI;
        this.io = io;
        System.out.println("--- Space App started ---");
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
                    String input = io.getScanner().nextLine().toLowerCase();

                    if (input.equals("q")) {
                        isRunning = false;
                    } else {
                        storeRole(input);
                    }
                    break;
            }
        }
        io.getScanner().close();
        System.out.println("Bye!");
    }

    private void runAgent(UI controller) {
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