package app.space.service;

import app.space.entity.Space;
import app.space.repo.ReservationRepo;
import app.space.repo.SpaceRepo;
import app.space.util.DuplicateIdException;

public class AdminService extends Service {
    public AdminService(ReservationRepo reservationRepo, SpaceRepo spaceRepo) {
        super(reservationRepo, spaceRepo);
    }

    public Space saveSpace(Space.Type type, String name, int price) {
        Space space = new Space(type, name, price);
        try {
            spaceRepo.save(space);
        } catch (DuplicateIdException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return space;
    }

    public boolean deleteSpace(int spaceId) {
        return spaceRepo.delete(spaceId);
    }
}
