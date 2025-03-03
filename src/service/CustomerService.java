package service;

import model.Reservation;
import model.Space;
import repo.ReservationRepo;
import repo.SpaceRepo;
import util.DublicateIdException;

import java.util.List;
import java.util.Map;

public class CustomerService extends Service {
    public CustomerService(ReservationRepo reservationRepo, SpaceRepo spaceRepo) {
        super(reservationRepo, spaceRepo);
    }

    public Reservation saveReservation(String ownerName, int spaceId, int date, int startHour, int endHour) {
        Space space = verifySpaceAvailability(spaceId, date, startHour, endHour);
        if (space != null) {
            try {
                Reservation reservation = new Reservation(ownerName, space, date, startHour, endHour);
                reservationRepo.save(reservation);
                return reservation;
            } catch (DublicateIdException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public Space verifySpaceAvailability(int spaceId, int date, int startHour, int endHour) {
        Space space = findSpaceById(spaceId);

        if (space != null) {
            List<Reservation> reservations = findReservationBySpaceId(spaceId);
            for (Reservation reservation : reservations) {
                if (reservation.getDate() == date) {
                    if ((startHour >= reservation.getStartHour() &&
                            startHour < reservation.getEndHour())
                            || (endHour > reservation.getStartHour() &&
                            endHour <= reservation.getEndHour())) {
                        return null;
                    }
                }
            }
        }

        return space;
    }

    public boolean deleteReservation(int reservationId) {
        return reservationRepo.delete(reservationId);
    }

    @Override
    public boolean storeInMemory() {
        return reservationRepo.persist();
    }
}
