package app.space.util;

public class PersistenceException extends Exception {
    public PersistenceException(Class entity, String details) {
        super("Error: Couldn't set up persistence for "
                + entity
                + ". Session might not be stored."
                + (details.isBlank() ? "" : "\nDetails: " + details));
    }
}
