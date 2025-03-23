package app.space.repo;
import app.space.entity.Space;
import app.space.util.DuplicateIdException;
import app.space.util.matcher.CriteriaMatcher;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SpaceRepo implements Repo<Space> {
    private final Map<Integer, Space> spaceMap;

    public SpaceRepo(RepoSource<Space> repoSource) {
        spaceMap = repoSource.getEntityMap();
    }

    public Optional<Space> findById(int id) {
        return Optional.ofNullable(spaceMap.get(id));
    }

    public List<Space> getAll() {
        return spaceMap.values().stream().toList();
    }

    public List<Space> findByCriteria(CriteriaMatcher<Space> matcher) {
        return spaceMap.values().stream()
                .filter(matcher::match)
                .toList();
    }

    public Space save(Space item) throws DuplicateIdException {
        Space res = spaceMap.putIfAbsent(item.getId(), item);
        if (res != null) throw new DuplicateIdException(item.getId(), item.getClass());
        return item;
    }

    public boolean delete(int id) {
        return spaceMap.remove(id) != null;
    }
}
