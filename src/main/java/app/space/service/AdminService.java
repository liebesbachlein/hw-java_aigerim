package main.java.app.space.service;

import main.java.app.space.model.Space;
import main.java.app.space.repo.ReservationRepo;
import main.java.app.space.repo.SpaceRepo;

public class AdminService extends Service {
    public AdminService(ReservationRepo reservationRepo, SpaceRepo spaceRepo) {
        super(reservationRepo, spaceRepo);
    }

    public Space saveSpace(Space.Type type, String name, int price) {
        Space space = new Space(type, name, price);
        spaceRepo.save(space);
        return space;
    }

    public boolean deleteSpace(int spaceId) {
        return spaceRepo.delete(spaceId);
    }
}
