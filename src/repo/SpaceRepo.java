package repo;

import model.Reservation;
import model.Space;
import util.DublicateIdException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpaceRepo implements Repo<Space> {
    private final Map<Integer, Space> idToSpace;

    public SpaceRepo() {
        idToSpace = new HashMap<>();
    }

    @Override
    public Space findById(int id) {
        return idToSpace.get(id);
    }

    @Override
    public List<Space> getAll() {
        return new ArrayList(idToSpace.values());
    }

    @Override
    public Space save(Space item) throws DublicateIdException {
        Space res = idToSpace.putIfAbsent(item.getId(), item);
        if (res != null) throw new DublicateIdException(item.getId(), item.getClass());
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (idToSpace.remove(id) == null) return false;
        return true;
    }
}
