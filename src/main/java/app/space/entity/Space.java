package app.space.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Space extends Entity {
    private int id;
    private final String name;
    private final int price;
    public final static String tableName = "spaces";

    public static String migration() {
        return //"DROP TABLE IF EXISTS `spaces`;\n" +
                "CREATE TABLE IF NOT EXISTS `spaces` (\n" +
                "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(64) NOT NULL,\n" +
                "  `price` int NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `name_UNIQUE` (`name`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
    }
}



