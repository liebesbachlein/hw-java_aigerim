package repo;

import model.Reservation;
import model.Space;
import util.DublicateIdException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpaceRepo extends PersistentRepo<Space> implements Repo<Space> {

    public SpaceRepo(String fileStorage) {
        super(fileStorage);
    }

    @Override
    public Space findById(int id) {
        return super.idToItem.get(id);
    }

    @Override
    public List<Space> getAll() {
        return new ArrayList(super.idToItem.values());
    }

    @Override
    public Space save(Space item) throws DublicateIdException {
        Space res = super.idToItem.putIfAbsent(item.getId(), item);
        if (res != null) throw new DublicateIdException(item.getId(), item.getClass());
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (super.idToItem.remove(id) == null) return false;
        return true;
    }
}
