package main.java.app.space.controller;

import main.java.app.space.model.Reservation;
import main.java.app.space.model.Space;
import main.java.app.space.service.Service;

import java.util.List;
import java.util.Scanner;

public abstract class Controller {
    protected final Scanner scanner;
    protected final Service service;

    protected Controller(Service service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    abstract public boolean run();

    abstract public void printRules();

    abstract protected void logOut();

    protected void printSpacesWithAvailability() {
        StringBuilder log = new StringBuilder();
        for (Space space : service.getAllSpaces()) {
            List<Reservation> reservations = service.findReservationBySpaceId(space.getId());
            for (Reservation reservation : reservations) {
                log.append(
                        "(!) Reserved on " + reservation.getDate()
                        + " from " + reservation.getStartHour() + ":00 to "
                        + reservation.getEndHour() + ":00.")
                        .append(System.getProperty("line.separator"));
            }

            log.append(space)
                    .append(System.getProperty("line.separator"))
                    .append(System.getProperty("line.separator"));;
        }

        if (log.isEmpty()) System.out.println("No spaces created yet!");
        else System.out.println(log);
    }

    protected void printReservations() {
        StringBuilder log = new StringBuilder();
        for (Reservation reservation : service.getAllReservations()) {
            log.append(reservation).append(System.getProperty("line.separator"));;
        }

        if (log.isEmpty()) System.out.println("No reservations made yet!");
        else System.out.println(log);
    }
}
