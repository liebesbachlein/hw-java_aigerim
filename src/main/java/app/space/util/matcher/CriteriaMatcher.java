package app.space.util.matcher;

@FunctionalInterface
public interface CriteriaMatcher<T> {
    boolean match(T item);


}
