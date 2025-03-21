package main.java.app.space.service;

import main.java.app.space.model.Reservation;
import main.java.app.space.model.Space;
import main.java.app.space.repo.ReservationRepo;
import main.java.app.space.repo.SpaceRepo;

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
}
