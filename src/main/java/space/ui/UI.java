package space.ui;

import org.springframework.stereotype.Component;
import space.config.IO;
import space.entity.Reservation;
import space.entity.Space;
import space.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public abstract class UI {
    protected final IO io;
    protected final AppService service;

    public UI(IO io, AppService service) {
        this.io = io;
        this.service = service;
    }

    abstract public boolean run();

    abstract public void printRules();

    abstract protected void logOut();

    protected void printSpacesWithAvailability() {
        StringBuilder out = new StringBuilder();
        Optional<List<Space>> allSpaces = service.getAllSpaces();
        if (allSpaces.isEmpty()) return;

        for (Space space : allSpaces.get()) {
            Optional<List<Reservation>> reservations = service.getReservationsBySpaceId(space.getId());
            if (reservations.isEmpty()) continue;
            for (Reservation reservation : reservations.get()) {
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
        Optional<List<Reservation>> reservations = service.getAllReservations();
        if (reservations.isEmpty()) return;

        for (Reservation reservation : reservations.get()) {
            log.append(reservation).append(System.lineSeparator());;
        }

        if (log.isEmpty()) System.out.println("No reservations made yet!");
        else System.out.println(log);
    }
}
