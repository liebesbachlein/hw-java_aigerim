package util;

//Narrow the bound in util/IdGenerator.generateId() to see the Exception in action
public class DublicateIdException extends Exception {
  public DublicateIdException(int id, Class entity) {
    super("Error: Duplicate ID " + id + " for " + entity + ". Duplicate IDs are not allowed.");
  }

  public DublicateIdException(String message) {
    super(message);
  }
}
