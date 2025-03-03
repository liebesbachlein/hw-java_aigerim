package repo;

import model.Space;
import util.DuplicateIdException;

import java.util.ArrayList;
import java.util.List;

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
    public Space save(Space item) throws DuplicateIdException {
        Space res = super.idToItem.putIfAbsent(item.getId(), item);
        if (res != null) throw new DuplicateIdException(item.getId(), item.getClass());
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (super.idToItem.remove(id) == null) return false;
        return true;
    }
}
