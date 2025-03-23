package app.space.service;

import app.space.entity.Reservation;
import app.space.entity.Space;
import app.space.repo.ReservationRepo;
import app.space.repo.SpaceRepo;
import app.space.util.matcher.ReservationCriteriaMatcher;
import app.space.util.matcher.SpaceCriteriaMatcher;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Service {
    protected final ReservationRepo reservationRepo;
    protected final SpaceRepo spaceRepo;

    protected Service(ReservationRepo reservationRepo, SpaceRepo spaceRepo) {
        this.reservationRepo = reservationRepo;
        this.spaceRepo = spaceRepo;
    }

    public List<Space> getAllSpaces() {
        return spaceRepo.getAll();
    }

    public List<Reservation> getAllReservations() {
        return reservationRepo.getAll();
    }

    public List<Reservation> getReservationsBySpaceId(int spaceId) {
        ReservationCriteriaMatcher matcher = new ReservationCriteriaMatcher
                .ReservationCriteriaMatcherBuilder()
                .spaceId(spaceId)
                .build();

        return reservationRepo.findByCriteria(matcher);
    }

    public List<Reservation> getReservationsByDate(int date) {
        ReservationCriteriaMatcher matcher = new ReservationCriteriaMatcher
                .ReservationCriteriaMatcherBuilder()
                .date(date)
                .build();

        return reservationRepo.findByCriteria(matcher);
    }

    public Optional<Space> checkSpaceAvailability(int spaceId, int date, int startHour, int endHour) {
        ReservationCriteriaMatcher matcher = new ReservationCriteriaMatcher
                .ReservationCriteriaMatcherBuilder()
                .spaceId(spaceId)
                .date(date)
                .hours(startHour, endHour)
                .build();

        Optional<Reservation> res = reservationRepo.findByCriteria(matcher).stream().findAny();

        if (res.isPresent()) {
            return Optional.empty();
        } else {
            return spaceRepo.findById(spaceId);
        }
    }
}
