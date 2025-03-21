package main.java.app.space.repo;

import main.java.app.space.model.Reservation;

import java.util.*;

public class ReservationRepo implements Repo<Reservation> {
    private final Map<Integer, Reservation> idToReservation;

    public ReservationRepo() {
        idToReservation = new HashMap<>();
    }

    @Override
    public Reservation findById(int id) {
        return idToReservation.get(id);
    }

    public List<Reservation> findBySpaceId(int id) {
        List<Reservation> foundReservations = new ArrayList<>();
        for (Reservation reservation : idToReservation.values()) {
            if (reservation.getSpace().getId() == id) foundReservations.add(reservation);
        }
        return foundReservations;
    }

    @Override
    public List<Reservation> getAll() {
        return new ArrayList(idToReservation.values());
    }

    public Map<Integer, List<Reservation>> getSpaceIdToReservation() {
        Map<Integer, List<Reservation>> spaceIdToReservation = new HashMap<>();
        for (Reservation reservation : idToReservation.values()) {
            int spaceId = reservation.getSpace().getId();
            if (spaceIdToReservation.containsKey(spaceId)) {
                spaceIdToReservation.get(spaceId).add(reservation);
            } else {
                List<Reservation> list = new ArrayList<>();
                list.add(reservation);
                spaceIdToReservation.put(spaceId, list);
            }
        }
        return spaceIdToReservation;
    }

    @Override
    public Reservation save(Reservation item) {
        return idToReservation.put(item.getId(), item);
    }

    @Override
    public boolean delete(int id) {
        if (idToReservation.remove(id) == null) return false;
        return true;
    }
}
