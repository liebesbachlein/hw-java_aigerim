package app.space.service;

import app.space.entity.Space;
import app.space.repo.ReservationRepo;
import app.space.repo.SpaceRepo;

import java.util.Optional;

public class AdminService extends Service {
    public AdminService(ReservationRepo reservationRepo, SpaceRepo spaceRepo) {
        super(reservationRepo, spaceRepo);
    }

    public Optional<Space> saveSpace(String name, int price) {
        return spaceRepo.save(new Space(name, price));
    }

    public boolean deleteSpace(int spaceId) {
        return spaceRepo.delete(spaceId);
    }
}
