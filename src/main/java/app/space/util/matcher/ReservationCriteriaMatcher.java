package app.space.util.matcher;
import app.space.entity.Reservation;


public class ReservationCriteriaMatcher implements CriteriaMatcher<Reservation> {
    // Criteria parameters
    private String ownerName;
    private int spaceId;
    private int date;
    private int startHour;
    private int endHour;

    private ReservationCriteriaMatcher(ReservationCriteriaMatcherBuilder builder) {
       ownerName = builder.ownerName;
       spaceId = builder.spaceId;
       date = builder.date;
       startHour = builder.startHour;
       endHour = builder.endHour;
    }

    private boolean matchOwnerName(String name) {
        return this.ownerName == null || this.ownerName.equals(name);
    }

    private boolean matchSpaceId(int spaceId) {
        return this.spaceId == -1 || this.spaceId == spaceId;
    }

    private boolean matchDate(int date) {
        return this.date == -1 || this.date == date;
    }

    private boolean matchHours(int startHour, int endHour) {
        return (this.endHour == -1 && this.startHour == -1)
                || (startHour >= this.startHour && startHour < this.endHour)
                || (endHour > this.startHour && endHour <= this.endHour)
                || (startHour <= this.startHour && endHour > this.startHour);
    }

    public boolean match(Reservation reservation) {
        return matchOwnerName(reservation.getOwnerName())
                && matchSpaceId(reservation.getSpaceId())
                && matchDate(reservation.getDate())
                && matchHours(reservation.getStartHour(), reservation.getEndHour());
    }

    public static class ReservationCriteriaMatcherBuilder {
        private String ownerName = null;
        private int spaceId = -1;
        private int date = -1;
        private int startHour = -1;
        private int endHour = -1;

        public ReservationCriteriaMatcherBuilder ownerName(String ownerName) {
            this.ownerName = ownerName;
            return this;
        }

        public ReservationCriteriaMatcherBuilder spaceId(int spaceId) {
            this.spaceId = spaceId;
            return this;
        }

        public ReservationCriteriaMatcherBuilder date(int date) {
            this.date = date;
            return this;
        }

        public ReservationCriteriaMatcherBuilder hours(int startHour, int endHour) {
            this.startHour = startHour;
            this.endHour = endHour;
            return this;
        }

        public ReservationCriteriaMatcher build(){
            return new ReservationCriteriaMatcher(this);
        }
    }
}

