package app.space.ui;

import app.space.model.Reservation;
import app.space.model.Space;
import app.space.service.Service;

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

    protected void printCalendar() {
        StringBuilder out = new StringBuilder();
        out.append("*****+ — indicates more than 5 reservation per this date").append(System.lineSeparator());
        out.append("   M      T      W      T      F      S      S   ").append(System.lineSeparator());
        for (int week = 0; week < 5; week++) {
            for (int day = 1; day <= 7; day++) {
                int date = day + 7 * week;
                if (date > 31) {
                    break;
                }

                switch(service.getReservationsByDate(date).size()) {
                    case 0 -> out.append("\s\s\s\s\s\s\s");
                    case 1 -> out.append("\s\s\s*\s\s\s");
                    case 2 -> out.append("\s\s**\s\s\s");
                    case 3 -> out.append("\s\s***\s\s");
                    case 4 -> out.append("\s****\s\s");
                    case 5 -> out.append("\s*****\s");
                    default -> out.append("*****+\s");
                }
            }
            out.append(System.lineSeparator());
            for (int day = 1; day <= 7; day++) {
                int date = day + 7 * week;
                if (date > 31) {
                    break;
                }

                if (date > 9) out.append("\s\s" + date + "\s\s\s");
                else out.append("\s\s\s" + date + "\s\s\s");
            }
            out.append(System.lineSeparator());
        }
        System.out.println(out);
    }

    protected void printSpacesWithAvailability() {
        StringBuilder out = new StringBuilder();
        for (Space space : service.getAllSpaces()) {
            for (Reservation reservation : service.getReservationsBySpaceId(space.getId())) {
                out.append(
                        "(!) Reserved on " + reservation.getDate()
                        + " from " + reservation.getStartHour() + ":00 to "
                        + reservation.getEndHour() + ":00.")
                        .append(System.lineSeparator());
            }

            out.append(space)
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());;
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

    public void storeSession() {
        if(!service.storeInMemory()) {
            System.out.println("(!) Error occurred while storing your session. Your changes might not be persisted.");
        }
    }
}
