package repo;

import model.Reservation;
import util.DublicateIdException;

import java.io.*;
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
    public Reservation save(Reservation item) throws DublicateIdException {
        Reservation res = idToReservation.putIfAbsent(item.getId(), item);
        if (res != null) throw new DublicateIdException(item.getId(), item.getClass());
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (idToReservation.remove(id) == null) return false;
        return true;
    }
}
