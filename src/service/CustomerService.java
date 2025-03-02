package service;

import model.Reservation;
import model.Space;
import repo.ReservationRepo;
import repo.SpaceRepo;

import java.util.List;
import java.util.Map;

public class CustomerService extends Service {
    public CustomerService(ReservationRepo reservationRepo, SpaceRepo spaceRepo) {
        super(reservationRepo, spaceRepo);
    }

    public void saveReservation(String ownerName, Space space, int date, int startHour, int endHour) {
        Reservation reservation = new Reservation(ownerName, space, date, startHour, endHour);
        reservationRepo.save(reservation);
    }

    public boolean deleteReservation(int reservationId) {
        return reservationRepo.delete(reservationId);
    }
}
