package app.space.service;

import app.space.entity.Reservation;
import app.space.entity.Space;
import app.space.repo.ReservationRepo;
import app.space.repo.SpaceRepo;
import app.space.util.DuplicateIdException;

public class CustomerService extends Service {
    public CustomerService(ReservationRepo reservationRepo, SpaceRepo spaceRepo) {
        super(reservationRepo, spaceRepo);
    }

    public Reservation saveReservation(String ownerName, int spaceId, int date, int startHour, int endHour) {
        Space space = checkSpaceAvailability(spaceId, date, startHour, endHour);
        if (space != null) {
            try {
                Reservation reservation = new Reservation(ownerName, space, date, startHour, endHour);
                reservationRepo.save(reservation);
                return reservation;
            } catch (DuplicateIdException e) {
                System.out.println(e.getMessage());
            }
        }

        return null;
    }

    public boolean deleteReservation(int reservationId) {
        return reservationRepo.delete(reservationId);
    }
}
