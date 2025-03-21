package app.space.config;

import app.space.entity.Reservation;
import app.space.entity.Space;
import app.space.repo.*;
import app.space.util.PersistenceException;
import lombok.Getter;


public class DataConfig {
    private static final DataConfig instance = new DataConfig();
    private final SpaceRepo spaceRepo;
    private final ReservationRepo reservationRepo;
    private RepoSource<Space> spaceRepoSource;
    private RepoSource<Reservation> reservationRepoSource;

    private DataConfig() {
       try {
           spaceRepoSource = new PersistentRepoSource<Space>(Space.class);
           reservationRepoSource = new PersistentRepoSource<Reservation>(Reservation.class);
        } catch (PersistenceException ex) {
           System.out.println(ex.getMessage());
           spaceRepoSource = new NonPersistentRepoSource<>();
           reservationRepoSource = new NonPersistentRepoSource<>();
        }
        spaceRepo = new SpaceRepo(spaceRepoSource);
        reservationRepo = new ReservationRepo(reservationRepoSource);
    }

    public void close() {
        spaceRepoSource.close();
        reservationRepoSource.close();
    }

    public SpaceRepo getSpaceRepo() {
        return spaceRepo;
    }

    public ReservationRepo getReservationRepo() {
        return reservationRepo;
    }

    public static DataConfig getInstance() {
        return instance;
    }
}
