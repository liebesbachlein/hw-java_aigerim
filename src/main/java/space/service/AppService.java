package space.service;

import space.entity.Reservation;
import space.entity.Space;
import space.repository.ReservationRepo;
import space.repository.SpaceRepo;
import space.util.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

public class AppService {
    private final ReservationRepo reservationRepo;
    private final SpaceRepo spaceRepo;

    public AppService(ReservationRepo reservationRepo, SpaceRepo spaceRepo) {
        this.reservationRepo = reservationRepo;
        this.spaceRepo = spaceRepo;
    }

    public Optional<List<Space>> getAllSpaces() {
        try {
            return Optional.of(spaceRepo.getAll());
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<List<Reservation>> getAllReservations() {
        try {
            return Optional.of(reservationRepo.getAll());
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<List<Reservation>> getReservationsBySpaceId(int spaceId) {
        try {
            return Optional.of(reservationRepo.findBySpaceId(spaceId));
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Space> checkSpaceAvailability(int spaceId, Date date, Time startHour, Time endHour) {
        try {
            List<Reservation> res = reservationRepo.findBySpaceIdAndDateAndTime(spaceId, date, startHour, endHour);
            return res.isEmpty() ? spaceRepo.findById(spaceId) : Optional.empty();
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }
}
