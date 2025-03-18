package app.space.repo;

import app.space.model.Space;
import app.space.util.DuplicateIdException;
import app.space.util.annotations.StreamAPI;
import app.space.util.matcher.CriteriaMatcher;

import java.util.List;

public class SpaceRepo extends PersistentRepo<Space> implements Repo<Space> {

    public SpaceRepo(String fileStorage) {
        super(fileStorage);
    }

    public Space findById(int id) {
        return super.idToItem.get(id);
    }

    @StreamAPI
    public List<Space> getAll() {
        return super.idToItem.values().stream().toList();
    }

    @StreamAPI
    public List<Space> findByCriteria(CriteriaMatcher<Space> matcher) {
        return super.idToItem.values().stream()
                .filter(matcher::match)
                .toList();
    }

    public Space save(Space item) throws DuplicateIdException {
        Space res = super.idToItem.putIfAbsent(item.getId(), item);
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
