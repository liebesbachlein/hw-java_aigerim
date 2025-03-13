package repo;

import model.Reservation;
import util.DuplicateIdException;
import util.annotations.StreamAPI;
import util.matcher.CriteriaMatcher;

import java.util.*;
import java.util.stream.Collectors;

public class ReservationRepo extends PersistentRepo<Reservation> implements Repo<Reservation> {
    public ReservationRepo(String fileStorage) {
        super(fileStorage);
    }

    public Reservation findById(int id) {
        return super.idToItem.get(id);
    }

    @StreamAPI
    public List<Reservation> findByCriteria(CriteriaMatcher<Reservation> matcher) {
        return super.idToItem.values().stream()
                .filter(matcher::match)
                .toList();
    }

    @StreamAPI
    public List<Reservation> getAll() {
        return super.idToItem.values().stream().toList();
    }

    @StreamAPI
    public Map<Integer, List<Reservation>> getSpaceIdToReservation() {
        return super.idToItem.values().stream()
               .collect(Collectors.groupingBy(Reservation::getSpaceId));
    }

    public Reservation save(Reservation item) throws DuplicateIdException {
        Reservation res = super.idToItem.putIfAbsent(item.getId(), item);
        if (res != null) throw new DuplicateIdException(item.getId(), item.getClass());
        return item;
    }

    public boolean delete(int id) {
        if (super.idToItem.remove(id) == null) return false;
        return true;
    }

    public int count() {
        return super.idToItem.size();
    }
}
