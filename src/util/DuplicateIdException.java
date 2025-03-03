package util;

//Narrow the bound in util/IdGenerator.generateId() to see the Exception in action
public class DuplicateIdException extends Exception {
  public DuplicateIdException(int id, Class entity) {
    super("Error: Duplicate ID " + id + " for " + entity + ". Duplicate IDs are not allowed.");
  }

  public DuplicateIdException(String message) {
    super(message);
  }
}
