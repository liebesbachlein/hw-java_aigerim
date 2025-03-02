package controller;

import model.Reservation;
import model.Space;
import service.Service;

import java.util.List;
import java.util.Scanner;

public abstract class Agent {
    protected final Scanner scanner;
    protected final Service service;

    protected Agent(Service service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    abstract public boolean run();

    abstract public void printRules();

    abstract protected void logOut();

    protected void showSpaceAvailability() {
        StringBuilder log = new StringBuilder();
        for (Space space : service.getAllSpaces()) {
            List<Reservation> reservations = service.findReservationBySpaceId(space.getId());
            for (Reservation reservation : reservations) {
                log.append("(!) Reserved on ")
                        .append(reservation.getDate())
                        .append(" from ")
                        .append(reservation.getStartHour())
                        .append(":00 to ")
                        .append(reservation.getEndHour())
                        .append(":00.\n");
            }

            log.append(space).append("\n\n");
        }

        if (log.isEmpty()) System.out.println("No spaces created yet!");
        else System.out.println(log);
    }

    protected void showReservations() {
        StringBuilder log = new StringBuilder();
        for (Reservation reservation : service.getAllReservations()) {
            log.append(reservation).append("\n");
        }

        if (log.isEmpty()) System.out.println("No reservations made yet!");
        else System.out.println(log);
    }
}
