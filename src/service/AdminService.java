package service;

import model.Reservation;
import model.Space;
import repo.ReservationRepo;
import repo.SpaceRepo;
import util.DublicateIdException;

import java.util.List;
import java.util.Map;

public class AdminService extends Service {
    public AdminService(ReservationRepo reservationRepo, SpaceRepo spaceRepo) {
        super(reservationRepo, spaceRepo);
    }

    public Space saveSpace(Space.Type type, String name, int price) {
        Space space = new Space(type, name, price);
        try {
            spaceRepo.save(space);
        } catch (DublicateIdException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return space;
    }

    public boolean deleteSpace(int spaceId) {
        return spaceRepo.delete(spaceId);
    }

    @Override
    public boolean storeInMemory() {
        return spaceRepo.persist();
    }
}
