package app.space.service;

import app.space.entity.Reservation;
import app.space.entity.Space;
import app.space.repo.ReservationRepo;
import app.space.repo.SpaceRepo;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

public class CustomerService extends Service {
    public CustomerService(ReservationRepo reservationRepo, SpaceRepo spaceRepo) {
        super(reservationRepo, spaceRepo);
    }

    public Optional<Reservation> saveReservation(String ownerName, int spaceId, Date date, Time startHour, Time endHour) {
        Optional<Space> space = checkSpaceAvailability(spaceId, date, startHour, endHour);
        if (space.isPresent()) {
                Reservation reservation = new Reservation(ownerName, space.get().getId(), date, startHour, endHour);
                return reservationRepo.save(reservation);
        }

        return Optional.empty();
    }

    public boolean deleteReservation(int reservationId) {
        return reservationRepo.delete(reservationId);
    }
}
