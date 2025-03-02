package model;

import util.IdGenerator;

import java.util.Date;

public class Reservation {
    private String ownerName;
    private Space space; // FK
    private int date;
    private int startHour;
    private int endHour;
    private int id;

    public Reservation(String ownerName, Space space, int date, int startHour, int endHour) {
        id = IdGenerator.generateId();
        this.ownerName = ownerName;
        this.space = space;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int getId() {
        return id;
    }

    public Space getSpace() {
        return space;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getDate() {
        return date;
    }

    public int getEndHour() {
        return endHour;
    }


    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", ownerName=" + ownerName +
                ", space=" + space +
                ", date=" + date +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                '}';
    }


    public String getOwnerName() {
        return ownerName;
    }
}
