package app.space.repo;

import app.space.entity.Reservation;
import app.space.util.DuplicateIdException;
import app.space.util.matcher.CriteriaMatcher;
import java.util.*;

public class ReservationRepo implements Repo<Reservation> {
    private final Map<Integer, Reservation> reservationMap;

    public ReservationRepo(RepoSource<Reservation> repoSource) {
        reservationMap = repoSource.getEntityMap();
    }

    public Optional<Reservation> findById(int id) {
        return Optional.ofNullable(reservationMap.get(id));
    }

    public List<Reservation> findByCriteria(CriteriaMatcher<Reservation> matcher) {
        return reservationMap.values().stream()
                .filter(matcher::match)
                .toList();
    }

    public List<Reservation> getAll() {
        return reservationMap.values().stream().toList();
    }

    public Reservation save(Reservation item) throws DuplicateIdException {
        Reservation res = reservationMap.putIfAbsent(item.getId(), item);
        if (res != null) throw new DuplicateIdException(item.getId(), item.getClass());
        return item;
    }

    public boolean delete(int id) {
        return reservationMap.remove(id) != null;
    }
}
