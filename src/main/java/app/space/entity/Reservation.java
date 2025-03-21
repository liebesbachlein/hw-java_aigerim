package app.space.entity;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Setter
@Getter
public class Reservation extends Entity implements Serializable {
    private String ownerName;
    private Space space; // FK
    private int date;
    private int startHour;
    private int endHour;

    public Reservation(String ownerName, Space space, int date, int startHour, int endHour) {
        super();
        this.ownerName = ownerName;
        this.space = space;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public Reservation(int id, String ownerName, Space space, int date, int startHour, int endHour) {
        super(id);
        this.ownerName = ownerName;
        this.space = space;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int getSpaceId() {
        return space.getId();
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + super.id +
                ", ownerName=" + ownerName +
                ", space=" + space +
                ", date=" + date +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                '}';
    }
}
