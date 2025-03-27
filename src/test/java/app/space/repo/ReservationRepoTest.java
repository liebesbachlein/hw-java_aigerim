package app.space.repo;

import app.space.config.DBConfig;
import app.space.config.LoggingConfig;
import app.space.entity.Reservation;
import app.space.entity.Space;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReservationRepoTest {
    private static ReservationRepo  reservationRepo;
    private static List<Space> spaces;
    private static List<Reservation> reservations;

    /*@BeforeAll
    static void setup() {
        DBConfig dbConfig = DBConfig.getInstance();
        Space space1 = new Space(1,"Cozy Space Test",1000);
        String s1 = "INSERT INTO `spaces` VALUES (1,'Cozy Space Test',1000);";
        Space space2 = new Space(2,"Nice Space Test",1000);
        String s2 = "INSERT INTO `spaces` VALUES (2,'Nice Space Test',1000);";
        Space space3 = new Space(3,"Kind Space Test",1000);
        String s3 = "INSERT INTO `spaces` VALUES (3,'Kind Space Test',1000);";
        Reservation reser1 = new Reservation(1, "me", 1,
                new Date(2025 - 1900, 7, 12),
                new Time(13, 0, 0),
                new Time(14, 0, 0));
        String r1 = "INSERT INTO `reservations` VALUES (1,'me','2025-08-12','13:00:00','14:00:00',1);";
        Reservation reser2 = new Reservation(2, "me", 1,
                new Date(2025 - 1900, 7, 12),
                new Time(14, 0, 0),
                new Time(18, 0, 0));
        String r2 = "INSERT INTO `reservations` VALUES (2,'me','2025-08-12','14:00:00','18:00:00',1);";
        Reservation reser3 = new Reservation(3, "me", 2,
                new Date(2025 - 1900, 7, 20),
                new Time(10, 0, 0),
                new Time(15, 0, 0));
        String r3 = "INSERT INTO `reservations` VALUES (3,'me','2025-08-20','10:00:00','15:00:00',2);";
        Reservation reser4 = new Reservation(4, "me", 2,
                new Date(2025 - 1900, 7, 20),
                new Time(18, 0, 0),
                new Time(21, 0, 0));
        String r4 = "INSERT INTO `reservations` VALUES (4,'me','2025-08-20','18:00:00','21:00:00',2);";
        Reservation reser5 = new Reservation(5, "me", 3,
                new Date(2025 - 1900, 7, 20),
                new Time(13, 0, 0),
                new Time(14, 0, 0));
        String r5 ="INSERT INTO `reservations` VALUES (5,'me','2025-08-20','13:00:00','14:00:00',3);";
        spaces = Arrays.asList(space1, space2, space3);
        reservations = Arrays.asList(reser1, reser2, reser3, reser4, reser5);

        String[] list = {s1, s2,  s3, r1, r2, r3, r4, r5};

        for (String item : list) {
            try(Statement statement = dbConfig.getDbConnection().createStatement()) {
                statement.execute(item);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        reservationRepo = dbConfig.getReservationRepo();
    }*/

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void findById_IdExists_OptionalOfReservation(int id) {
        Optional<Reservation> res = reservationRepo.findById(id);

        assertTrue(res.isPresent());
        assertEquals(id, res.get().getId());
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 20, 30})
    void findById_IdNotExists_OptionalEmpty(int id) {
        Optional<Reservation> res = reservationRepo.findById(id);

        assertTrue(res.isEmpty());
    }

    /*@ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void findBySpaceId_SpaceIdExists_ListOfReservations(int spaceId) {
        Object[] expected = reservations.stream().filter(e -> spaceId == e.getSpaceId()).toArray();
        List<Reservation> res = reservationRepo.findBySpaceId(spaceId);
        assertArrayEquals(expected, res.toArray());
    }*/
}