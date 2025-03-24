package app.space.config;

import app.space.entity.Reservation;
import app.space.entity.Space;
import app.space.repo.*;
import app.space.util.PersistenceException;


public class DataConfig {
    private static final DataConfig instance = new DataConfig();
    private final SpaceRepo spaceRepo;
    private final ReservationRepo reservationRepo;
    private DataSource<Space> spaceDataSource;
    private DataSource<Reservation> reservationDataSource;

    private DataConfig() {
       try {
           spaceDataSource = new PersistentDataSource<Space>(Space.class);
           reservationDataSource = new PersistentDataSource<Reservation>(Reservation.class);
        } catch (PersistenceException ex) {
           System.out.println(ex.getMessage());
           spaceDataSource = new NonPersistentDataSource<>();
           reservationDataSource = new NonPersistentDataSource<>();
        }
        spaceRepo = new SpaceRepo(spaceDataSource);
        reservationRepo = new ReservationRepo(reservationDataSource);
    }

    public void close() {
        spaceDataSource.close();
        reservationDataSource.close();
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
