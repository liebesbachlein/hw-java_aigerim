package app.space.service;

import app.space.entity.Space;
import app.space.repo.ReservationRepo;
import app.space.repo.SpaceRepo;
import app.space.util.DuplicateIdException;

import java.util.Optional;

public class AdminService extends Service {
    public AdminService(ReservationRepo reservationRepo, SpaceRepo spaceRepo) {
        super(reservationRepo, spaceRepo);
    }

    public Optional<Space> saveSpace(Space.Type type, String name, int price) {
        Space space = new Space(name, type, price);
        try {
            spaceRepo.save(space);
        } catch (DuplicateIdException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
        return Optional.of(space);
    }

    public boolean deleteSpace(int spaceId) {
        return spaceRepo.delete(spaceId);
    }
}
