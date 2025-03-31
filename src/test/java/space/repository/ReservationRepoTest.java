package space.repository;

class ReservationRepoTest {
    /*private ReservationRepo reservationRepo = new ReservationRepo();
    private static List<Space> spaces;
    private static List<Reservation> reservations;

    @BeforeAll
    static void setup() {
        new DBConfig();
        String spaceTable = "CREATE TABLE `spaces` (\n" +
                "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(255) NOT NULL,\n" +
                "  `price` int NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ");";
        String reservationTable = "CREATE TABLE `reservations` (\n" +
                "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                "  `date` date NOT NULL,\n" +
                "  `endHour` time(6) NOT NULL,\n" +
                "  `ownerName` varchar(255) NOT NULL,\n" +
                "  `startHour` time(6) NOT NULL,\n" +
                "  `space_id` int DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `FKamig3ih2d03a4kb15cuoax1rm` (`space_id`),\n" +
                "  CONSTRAINT `FKamig3ih2d03a4kb15cuoax1rm` FOREIGN KEY (`space_id`) REFERENCES `spaces` (`id`)\n" +
                ");";
        Space space1 = new Space(1,"Cozy Space Test",1000);
        String s1 = "INSERT INTO `spaces` (id, name, price) VALUES (1,'Cozy Space Test',1000);";
        Space space2 = new Space(2,"Nice Space Test",1000);
        String s2 = "INSERT INTO `spaces` (id, name, price) VALUES (2,'Nice Space Test',1000);";
        Space space3 = new Space(3,"Kind Space Test",1000);
        String s3 = "INSERT INTO `spaces` (id, name, price) VALUES (3,'Kind Space Test',1000);";
        Reservation reser1 = new Reservation(1, "me", space1,
                new Date(2025 - 1900, 7, 12),
                new Time(13, 0, 0),
                new Time(14, 0, 0));
        String r1 = "INSERT INTO `reservations` (id, ownerName, date, startHour, endHour, space_id) VALUES (1,'me','2025-08-12','13:00:00','14:00:00',1);";
        Reservation reser2 = new Reservation(2, "me", space1,
                new Date(2025 - 1900, 7, 12),
                new Time(14, 0, 0),
                new Time(18, 0, 0));
        String r2 = "INSERT INTO `reservations` (id, ownerName, date, startHour, endHour, space_id) VALUES (2,'me','2025-08-12','14:00:00','18:00:00',1);";
        Reservation reser3 = new Reservation(3, "me", space2,
                new Date(2025 - 1900, 7, 20),
                new Time(10, 0, 0),
                new Time(15, 0, 0));
        String r3 = "INSERT INTO `reservations` (id, ownerName, date, startHour, endHour, space_id) VALUES (3,'me','2025-08-20','10:00:00','15:00:00',2);";
        Reservation reser4 = new Reservation(4, "me", space2,
                new Date(2025 - 1900, 7, 20),
                new Time(18, 0, 0),
                new Time(21, 0, 0));
        String r4 = "INSERT INTO `reservations` (id, ownerName, date, startHour, endHour, space_id) VALUES (4,'me','2025-08-20','18:00:00','21:00:00',2);";
        Reservation reser5 = new Reservation(5, "me", space3,
                new Date(2025 - 1900, 7, 20),
                new Time(13, 0, 0),
                new Time(14, 0, 0));
        String r5 ="INSERT INTO `reservations` (id, ownerName, date, startHour, endHour, space_id) VALUES (5,'me','2025-08-20','13:00:00','14:00:00',3);";
        spaces = Arrays.asList(space1, space2, space3);
        reservations = Arrays.asList(reser1, reser2, reser3, reser4, reser5);

        String[] list = {spaceTable, reservationTable, s1, s2,  s3, r1, r2, r3, r4, r5};

        EntityManager em = DBConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for(String item : list) {
            Query q = em.createNativeQuery(item);
            q.executeUpdate();
        }
        transaction.commit();
    }

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

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void findBySpaceId_SpaceIdExists_ListOfReservations(int spaceId) {
        Object[] expected = reservations.stream().filter(e -> spaceId == e.getSpace().getId()).toArray();
        List<Reservation> res = reservationRepo.findBySpaceId(spaceId);
        assertArrayEquals(expected, res.toArray());
    }*/
}