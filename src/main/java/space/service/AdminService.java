package space.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.entity.Space;
import space.entity.SpaceType;
import space.repository.ReservationRepo;
import space.repository.SpaceRepo;
import space.repository.SpaceTypeRepo;
import space.util.RepositoryException;

import java.util.List;

@Slf4j
@Service
public class AdminService {
    private final ReservationRepo reservationRepo;
    private final SpaceRepo spaceRepo;
    private final SpaceTypeRepo spaceTypeRepo;

    @Autowired
    public AdminService(
            ReservationRepo reservationRepo, SpaceRepo spaceRepo, SpaceTypeRepo spaceTypeRepo) {
        this.reservationRepo = reservationRepo;
        this.spaceRepo = spaceRepo;
        this.spaceTypeRepo = spaceTypeRepo;
    }

    public List<Space> getAllSpaces() {
        return spaceRepo.getAll();
    }

    public Space saveSpace(String name, int price, int spaceTypeId) throws RepositoryException {
        return spaceRepo.save(new Space(0, name, price, new SpaceType(spaceTypeId, "")));
    }

    public void deleteSpace(int spaceId) throws RepositoryException {
        spaceRepo.delete(spaceId);
    }

    public SpaceType saveSpaceType(String name) throws RepositoryException {
       return spaceTypeRepo.save(new SpaceType(0, name));
    }

    public void deleteSpaceType(int spaceTypeId) throws RepositoryException {
        spaceTypeRepo.delete(spaceTypeId);
    }

    public List<SpaceType> getAllSpaceTypes() {
        return spaceTypeRepo.getAll();
    }
}
