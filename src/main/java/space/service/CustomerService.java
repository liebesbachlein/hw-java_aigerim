package space.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import space.entity.Reservation;
import space.entity.Space;
import space.entity.User;
import space.repository.ReservationRepo;
import space.repository.SpaceRepo;
import space.util.RepositoryException;
import space.util.ReservationTimeConflictException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerService {
    private final ReservationRepo reservationRepo;
    private final SpaceRepo spaceRepo;

    public CustomerService(ReservationRepo reservationRepo, SpaceRepo spaceRepo) {
        this.reservationRepo = reservationRepo;
        this.spaceRepo = spaceRepo;
    }

    public Reservation createReservation(
            User owner,
            Space space,
            LocalDate date,
            LocalTime hour) throws RepositoryException, ReservationTimeConflictException {
        if (isSpaceReservable(space.getId(), date, hour)) {
            Reservation reservation = new Reservation(
                    0, owner, space, date, hour);
            return reservationRepo.save(reservation);
        } else {
            throw new ReservationTimeConflictException();
        }
    }

    public void cancelReservation(int reservationId) throws RepositoryException {
        reservationRepo.delete(reservationId);
    }

    public List<Reservation> getAllReservations() throws RepositoryException {
        return reservationRepo.getAll();
    }

    public List<Reservation> getReservationsBySpaceId(int spaceId) {
        return reservationRepo.findBySpaceId(spaceId);
    }

    public List<Reservation> getReservationsByUserId(int userId) {
        return reservationRepo.findByOwnerId(userId);
    }

    public boolean isReservedByUser(int reservationId, int userId) {
        return reservationRepo.findByOwnerId(userId).stream().anyMatch(e -> e.getId() == reservationId);
    }

    public List<Space> getAllSpaces() {
        return spaceRepo.getAll();
    }

    public Optional<Space> getSpace(int spaceId) {
        return spaceRepo.findById(spaceId);
    }

    public List<Reservation> getReservationsBySpacesIdAndDate(int spaceId, LocalDate date) {
        return reservationRepo.findBySpaceIdAndDate(spaceId, date);
    }

    private boolean isSpaceReservable(
            int spaceId,
            LocalDate date,
            LocalTime hour) {
        if (date == null || date.isBefore(LocalDate.now())) return false;
        List<Reservation> res = reservationRepo
                .findBySpaceIdAndDateAndTime(spaceId, date, hour);
        return res.isEmpty();
    }
}
