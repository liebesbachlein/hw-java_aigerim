package space.config;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class IO {
    private final Scanner scanner = new Scanner(System.in);;

    public Scanner getScanner() {
        return scanner;
    }
}
