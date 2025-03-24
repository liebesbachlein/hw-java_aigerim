package app.space.ui;

import app.space.entity.Reservation;
import app.space.entity.Space;
import app.space.service.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public abstract class UI {
    protected final Scanner scanner;
    protected final Service service;

    protected UI(Service service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    abstract public boolean run();

    abstract public void printRules();

    abstract protected void logOut();

    protected void printSpacesWithAvailability() {
        StringBuilder out = new StringBuilder();
        for (Space space : service.getAllSpaces()) {
            for (Reservation reservation : service.getReservationsBySpaceId(space.getId())) {
                out.append("(!) Reserved on ")
                        .append(reservation.getDate())
                        .append(" from ")
                        .append(reservation.getStartHour())
                        .append(":00 to ")
                        .append(reservation.getEndHour())
                        .append(":00.")
                        .append(System.lineSeparator());
            }

            out.append(space).append(System.lineSeparator());
        }

        if (out.isEmpty()) System.out.println("No spaces created yet!");
        else System.out.println(out);
    }

    protected void printReservations() {
        StringBuilder log = new StringBuilder();
        for (Reservation reservation : service.getAllReservations()) {
            log.append(reservation).append(System.lineSeparator());;
        }

        if (log.isEmpty()) System.out.println("No reservations made yet!");
        else System.out.println(log);
    }
}
