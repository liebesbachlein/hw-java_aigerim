package space.util;

import lombok.extern.slf4j.Slf4j;

public class RepositoryException extends Exception {
    public RepositoryException(String message) {
        super("RepositoryException: " + message);
    }
}
