package app.space.util.matcher;
import app.space.entity.Reservation;

public class ReservationCriteriaMatcher implements CriteriaMatcher<Reservation> {
    private String ownerName;
    private int spaceId;
    private int startDate;
    private int endDate;
    private int startHour;
    private int endHour;

    private ReservationCriteriaMatcher(ReservationCriteriaMatcherBuilder builder) {
       ownerName = builder.ownerName;
       spaceId = builder.spaceId;
       startDate = builder.startDate;
       endDate = builder.endDate;
       startHour = builder.startHour;
       endHour = builder.endHour;
    }

    public boolean matchOwnerName(String name) {
        return this.ownerName == null || this.ownerName.equals(name);
    }

    public boolean matchSpaceId(int spaceId) {
        return this.spaceId == -1 || this.spaceId >= spaceId;
    }

    public boolean matchStartDateLoose(int startDate) {
        return this.startDate == -1 || this.startDate >= startDate;
    }

    public boolean matchEndDateLoose(int endDate) {
        return this.endDate == -1 || this.endDate <= endDate;
    }

    public boolean matchStartHourLoose(int startHour) {
        return this.startHour == -1 || this.startHour >= startHour;
    }

    public boolean matchEndHourLoose(int endHour) {
        return this.endHour == -1 || this.endHour <= endHour;
    }

    public boolean match(Reservation reservation) {
        return matchOwnerName(reservation.getOwnerName())
                && matchSpaceId(reservation.getSpaceId())
                && matchStartDateLoose(reservation.getDate())
                && matchEndDateLoose(reservation.getDate())
                && matchStartHourLoose(reservation.getStartHour())
                && matchEndHourLoose(reservation.getEndHour());
    }

    public static class ReservationCriteriaMatcherBuilder {
        private String ownerName = null;
        private int spaceId = -1;
        private int startDate = -1;
        private int endDate = -1;
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

        public ReservationCriteriaMatcherBuilder startDate(int startDate) {
            this.startDate = startDate;
            return this;
        }

        public ReservationCriteriaMatcherBuilder endDate(int endDate) {
            this.endDate = endDate;
            return this;
        }

        public ReservationCriteriaMatcherBuilder startHour(int startHour) {
            this.startHour = startHour;
            return this;
        }

        public ReservationCriteriaMatcherBuilder endHour(int endHour) {
            this.endHour = endHour;
            return this;
        }

        public ReservationCriteriaMatcher build(){
            return new ReservationCriteriaMatcher(this);
        }
    }
}

