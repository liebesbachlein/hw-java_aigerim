package service;

import model.Reservation;
import model.Space;
import repo.ReservationRepo;
import repo.SpaceRepo;

import java.util.List;
import java.util.Map;

public class AdminService extends Service {
    public AdminService(ReservationRepo reservationRepo, SpaceRepo spaceRepo) {
        super(reservationRepo, spaceRepo);
    }

    public void saveSpace(Space.Type type, String name, int price) {
        Space space = new Space(type, name, price);
        spaceRepo.save(space);
    }

    public boolean deleteSpace(int spaceId) {
        return spaceRepo.delete(spaceId);
    }
}
