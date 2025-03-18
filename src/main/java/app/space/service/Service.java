package app.space.service;

import app.space.model.Reservation;
import app.space.model.Space;
import app.space.repo.ReservationRepo;
import app.space.repo.SpaceRepo;
import app.space.util.annotations.Lambda;
import app.space.util.annotations.StreamAPI;
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
                .startDate(date)
                .endDate(date)
                .build();

        return reservationRepo.findByCriteria(matcher);
    }

    @StreamAPI
    @Lambda
    public List<Space> getSpacesByAvailabilityAndPrice(
            int startDate, int endDate, int startHour, int endHour, int startPrice, int endPrice) {
        ReservationCriteriaMatcher resMatcher = new ReservationCriteriaMatcher
                .ReservationCriteriaMatcherBuilder()
                .startDate(startDate)
                .endDate(endDate)
                .startHour(startHour)
                .endHour(endHour)
                .build();

        SpaceCriteriaMatcher priceMatcher = new SpaceCriteriaMatcher
                .SpaceCriteriaMatcherBuilder()
                .startPrice(startPrice)
                .endPrice(endPrice)
                .build();

        List<Space> bookedSpaces = reservationRepo.findByCriteria(resMatcher).stream()
                .map(e -> e.getSpace()).toList();

        Predicate<Space> isSpaceAvailable = s -> bookedSpaces.contains(s);

        return spaceRepo.getAll().stream().filter(isSpaceAvailable).filter(priceMatcher::match).toList();
    }

    @StreamAPI
    @app.space.util.annotations.Optional
    public Space checkSpaceAvailability(int spaceId, int date, int startHour, int endHour) {
        ReservationCriteriaMatcher matcher = new ReservationCriteriaMatcher
                .ReservationCriteriaMatcherBuilder()
                .spaceId(spaceId)
                .startDate(date)
                .endDate(date)
                .startHour(startHour)
                .endHour(endHour)
                .build();

        Optional<Reservation> res = reservationRepo.findByCriteria(matcher).stream().findAny();

        if (res.isPresent()) {
            return null;
        } else {
            return spaceRepo.findById(spaceId);
        }
    }

    public boolean storeInMemory() {
        return reservationRepo.persist() && spaceRepo.persist();
    }
}
