package space.util;

public class RepositoryException extends Exception {
    public RepositoryException(String message) {
        super("Error on Repository Level: " + message);
    }
}
