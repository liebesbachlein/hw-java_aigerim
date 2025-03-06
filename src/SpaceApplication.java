import config.DataConfig;
import config.IOConfig;
import config.LoggingConfig;
import controller.AdminController;
import controller.Controller;
import controller.CustomerController;
import service.AdminService;
import service.CustomerService;

public class SpaceApplication {
    private enum Role {
        ADMIN,
        CUSTOMER,
        NONE
    }

    private final AdminController adminAgent;
    private final CustomerController customerAgent;
    private final IOConfig ioConfig;
    private final DataConfig dataConfig;
    private final LoggingConfig loggingConfig;
    private Role role = Role.NONE;

    public SpaceApplication() {
        ioConfig = IOConfig.getInstance();
        dataConfig = DataConfig.getInstance();
        loggingConfig = LoggingConfig.getInstance();
        System.out.println("--- Space App started ---");

        adminAgent = new AdminController(new AdminService(dataConfig.getReservationRepo(), dataConfig.getSpaceRepo()), ioConfig.getScanner());
        customerAgent = new CustomerController(new CustomerService(dataConfig.getReservationRepo(), dataConfig.getSpaceRepo()), ioConfig.getScanner());
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
        System.out.println("Bye!");
    }

    private void runAgent(Controller controller) {
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