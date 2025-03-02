package repo;

import model.Reservation;

import java.util.*;

public class ReservationRepo implements Repo<Reservation> {
    private final Map<Integer, Reservation> idToReservation;
    private final Map<Integer, List<Reservation>> spaceIdToReservation;

    public ReservationRepo() {
        idToReservation = new HashMap<>();
        spaceIdToReservation = new HashMap<>();
    }

    @Override
    public Reservation findById(int id) {
        return idToReservation.get(id);
    }

    public List<Reservation> findBySpaceId(int id) {
        List<Reservation> res = spaceIdToReservation.get(id);
        if (res == null) return new ArrayList<Reservation>();
        return res;
    }

    @Override
    public List<Reservation> getAll() {
        return new ArrayList(idToReservation.values());
    }

    public Map<Integer, List<Reservation>> getSpaceIdToReservation() {
        return spaceIdToReservation;
    }

    @Override
    public void save(Reservation item) {
        idToReservation.put(item.getId(), item);

        if(spaceIdToReservation.containsKey(item.getSpace().getId())) {
            spaceIdToReservation.get(item.getSpace().getId()).add(item);
        } else {
            List<Reservation> list = new ArrayList<>();
            list.add(item);
            spaceIdToReservation.put(item.getSpace().getId(), list);
        }
    }

    @Override
    public boolean delete(int id) {
        Reservation reservation = idToReservation.remove(id);
        if (reservation == null) return false;

        List<Reservation> reservations = spaceIdToReservation.get(reservation.getSpace().getId());
        reservations.remove(reservation);

        if (reservations.isEmpty()) {
            spaceIdToReservation.remove(reservation.getSpace().getId());
        }

        return true;
    }
}
