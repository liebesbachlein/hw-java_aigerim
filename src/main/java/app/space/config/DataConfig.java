package app.space.config;

import app.space.repo.ReservationRepo;
import app.space.repo.SpaceRepo;

public class DataConfig {
    private static final DataConfig instance = new DataConfig();
    private final SpaceRepo spaceRepo;
    private final ReservationRepo reservationRepo;
    private static boolean isSessionPersistable;

    private DataConfig() {
        spaceRepo = new SpaceRepo("space_storage");
        reservationRepo = new ReservationRepo("reserv_storage");
        configurePersistence(spaceRepo, reservationRepo);
    }

    private static void configurePersistence(SpaceRepo spaceRepo, ReservationRepo reservationRepo) {
        if (spaceRepo.init() && reservationRepo.init()) {
            isSessionPersistable = true;
        } else {
            isSessionPersistable = false;
            spaceRepo.disablePersistence();
            reservationRepo.disablePersistence();
            System.out.println("(!) Error occurred while configuring persistence. Your session will not be stored.");
        }
    }

    public static boolean isSessionPersistable() {
        return isSessionPersistable;
    }

    public static DataConfig getInstance() {
        return instance;
    }

    public SpaceRepo getSpaceRepo() {
        return spaceRepo;
    }

    public ReservationRepo getReservationRepo() {
        return reservationRepo;
    }
}
