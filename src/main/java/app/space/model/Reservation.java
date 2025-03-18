package app.space.model;

import lombok.Getter;
import app.space.util.IdGenerator;

import java.io.Serializable;

@Getter
public class Reservation implements Serializable {
    private String ownerName;
    private Space space; // FK
    private int date;
    private int startHour;
    private int endHour;
    private int id;

    public int getSpaceId() {
        return space.getId();
    }

    public Reservation(String ownerName, Space space, int date, int startHour, int endHour) {
        id = IdGenerator.generateId();
        this.ownerName = ownerName;
        this.space = space;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
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
}
