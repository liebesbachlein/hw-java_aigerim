package app.space.entity;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Getter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Reservation extends Entity {
    private int id;
    private final String ownerName;
    private final int spaceId;
    private final Date date;
    private final Time startHour;
    private final Time endHour;
    public final static String tableName = "reservations";

    public static String migration() {
        return  //"DROP TABLE IF EXISTS `reservations`;\n" +
                "CREATE TABLE IF NOT EXISTS `reservations` (\n" +
                "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                "  `owner_name` varchar(64) NOT NULL,\n" +
                "  `date` date NOT NULL,\n" +
                "  `start_hour` time NOT NULL,\n" +
                "  `end_hour` time NOT NULL,\n" +
                "  `space_id` int NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `spaces_id_foreign_idx` (`space_id`),\n" +
                "  CONSTRAINT `spaces_id_foreign` FOREIGN KEY (`space_id`) REFERENCES `spaces` (`id`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
    }
}
