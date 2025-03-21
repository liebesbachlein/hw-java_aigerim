package app.space.repo;

import app.space.entity.Entity;
import app.space.entity.Reservation;
import app.space.util.DuplicateIdException;
import app.space.util.PersistenceException;
import app.space.util.matcher.CriteriaMatcher;

import java.util.*;
import java.util.stream.Collectors;

public class ReservationRepo implements Repo<Reservation> {
    private final Map<Integer, Reservation> reservationMap;

    public ReservationRepo(RepoSource<Reservation> repoSource) {
        reservationMap = repoSource.getEntityMap();
    }

    public Reservation findById(int id) {
        return reservationMap.get(id);
    }

    public List<Reservation> findByCriteria(CriteriaMatcher<Reservation> matcher) {
        return reservationMap.values().stream()
                .filter(matcher::match)
                .toList();
    }

    public List<Reservation> getAll() {
        return reservationMap.values().stream().toList();
    }

    public Map<Integer, List<Reservation>> getSpaceIdToReservation() {
        return reservationMap.values().stream()
               .collect(Collectors.groupingBy(Reservation::getSpaceId));
    }

    public Reservation save(Reservation item) throws DuplicateIdException {
        Reservation res = reservationMap.putIfAbsent(item.getId(), item);
        if (res != null) throw new DuplicateIdException(item.getId(), item.getClass());
        return item;
    }

    public boolean delete(int id) {
        if (reservationMap.remove(id) == null) return false;
        return true;
    }
}
