package app.space.util;

//import java.util.Random;

import java.util.Random;

public class IdGenerator {
    public static int generateId() {
        return new Random().nextInt(900) + 100;
    }
}
