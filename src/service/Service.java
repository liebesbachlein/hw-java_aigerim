package service;

import model.Reservation;
import model.Space;
import repo.PersistentRepo;
import repo.ReservationRepo;
import repo.SpaceRepo;

import java.util.List;
import java.util.Map;

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

    public Space findSpaceById(int id) {
        return spaceRepo.findById(id);
    }

    public Map<Integer, List<Reservation>> getSpaceIdToReservation() {
        return reservationRepo.getSpaceIdToReservation();
    }

    public List<Reservation> getAllReservations() {
        return reservationRepo.getAll();
    }

    public List<Reservation> findReservationBySpaceId(int id) {
        return reservationRepo.findBySpaceId(id);
    }

    public boolean storeInMemory() {
        return reservationRepo.persist() && spaceRepo.persist();
    }
}
