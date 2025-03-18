package app.space.config;

import app.space.util.CustomClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LoggingConfig {
    private static final LoggingConfig instance = new LoggingConfig();
    private Method writeLog;

    private LoggingConfig() {
        CustomClassLoader classLoader = new CustomClassLoader();

        try {
            Class<?> logger = classLoader.loadClass("Logger");
            logger.getDeclaredConstructor().newInstance(); // runs static block
            writeLog = logger.getMethod("write", String.class);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException
                ex ) {
            System.out.print("Error occurred while configuring logger: ");
            System.out.println(ex.getMessage());
        }
    }

    public void logInfo(String message) {
        try {
            writeLog.invoke(null, message);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            System.out.println("Error occurred while logging a message.");
        }
    }

    public static LoggingConfig getInstance() {
        return instance;
    }
}
