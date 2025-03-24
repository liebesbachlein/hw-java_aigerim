package app.space.service;

import app.space.entity.Reservation;
import app.space.entity.Space;
import app.space.repo.ReservationRepo;
import app.space.repo.SpaceRepo;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

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
        return reservationRepo.findBySpaceId(spaceId);
    }

    public Optional<Space> checkSpaceAvailability(int spaceId, Date date, Time startHour, Time endHour) {
        List<Reservation> res = reservationRepo.findBySpaceIdAndDateAndTime(spaceId, date, startHour, endHour);

        if (res.isEmpty()) {
            return spaceRepo.findById(spaceId);
        } else {
            return Optional.empty();
        }
    }
}
