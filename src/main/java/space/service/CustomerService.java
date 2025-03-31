package space.service;

import org.springframework.beans.factory.annotation.Autowired;
import space.entity.Reservation;
import space.entity.Space;
import space.repository.ReservationRepo;
import space.repository.SpaceRepo;
import space.util.RepositoryException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

@Service
public class CustomerService extends AppService {
    private final ReservationRepo reservationRepo;
    private final SpaceRepo spaceRepo;

    @Autowired
    public CustomerService(ReservationRepo reservationRepo, SpaceRepo spaceRepo) {
        super(reservationRepo, spaceRepo);
        this.reservationRepo = reservationRepo;
        this.spaceRepo = spaceRepo;
    }

    public Optional<Reservation> saveReservation(String ownerName, int spaceId, Date date, Time startHour, Time endHour) {
        Optional<Space> space = checkSpaceAvailability(spaceId, date, startHour, endHour);
        if (space.isPresent()) {
            Reservation reservation = new Reservation(ownerName, space.get(), date, startHour, endHour);
            try {
                return reservationRepo.save(reservation);
            } catch (RepositoryException e) {
                System.err.println(e.getMessage());
                return Optional.empty();
            }
        } else return Optional.empty();
    }

    public Optional<Boolean> deleteReservation(int reservationId) {
        try {
            return Optional.of(reservationRepo.delete(reservationId));
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }
}
