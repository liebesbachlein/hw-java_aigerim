package app.space.service;

import app.space.entity.Reservation;
import app.space.entity.Space;
import app.space.repo.ReservationRepo;
import app.space.repo.SpaceRepo;
import app.space.util.DuplicateIdException;

import java.util.Optional;

public class CustomerService extends Service {
    public CustomerService(ReservationRepo reservationRepo, SpaceRepo spaceRepo) {
        super(reservationRepo, spaceRepo);
    }

    public Optional<Reservation> saveReservation(String ownerName, int spaceId, int date, int startHour, int endHour) {
        Optional<Space> space = checkSpaceAvailability(spaceId, date, startHour, endHour);
        if (space.isPresent()) {
            try {
                Reservation reservation = new Reservation(ownerName, space.get(), date, startHour, endHour);
                reservationRepo.save(reservation);
                return Optional.of(reservation);
            } catch (DuplicateIdException e) {
                System.out.println(e.getMessage());
            }
        }

        return Optional.empty();
    }

    public boolean deleteReservation(int reservationId) {
        return reservationRepo.delete(reservationId);
    }
}
