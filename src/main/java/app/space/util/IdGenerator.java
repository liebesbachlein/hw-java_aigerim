package main.java.app.space.util;

import java.util.random.RandomGenerator;

public class IdGenerator {
    public static int generateId() {
        return RandomGenerator.getDefault().nextInt(100, 1000);
    }
}
