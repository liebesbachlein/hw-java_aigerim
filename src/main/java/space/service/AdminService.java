package space.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import space.entity.Space;
import space.repository.ReservationRepo;
import space.repository.SpaceRepo;
import space.util.RepositoryException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService extends AppService {
    private final ReservationRepo reservationRepo;
    private final SpaceRepo spaceRepo;

    @Autowired
    public AdminService(ReservationRepo reservationRepo, SpaceRepo spaceRepo) {
        super(reservationRepo, spaceRepo);
        this.reservationRepo = reservationRepo;
        this.spaceRepo = spaceRepo;
    }

    @Transactional
    public Optional<Space> saveSpace(String name, int price) {
        try {
            return spaceRepo.save(new Space(name, price));
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Boolean> deleteSpace(int spaceId) {
        try {
            return Optional.of(spaceRepo.delete(spaceId));
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }
}
