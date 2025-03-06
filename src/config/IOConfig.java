package config;

import java.util.Scanner;

public class IOConfig {
    private static final IOConfig instance = new IOConfig();
    private final Scanner scanner;

    private IOConfig() {
        scanner = new Scanner(System.in);
    }

    public Scanner getScanner() {
        return scanner;
    }

    public static IOConfig getInstance() {
        return instance;
    }
}
