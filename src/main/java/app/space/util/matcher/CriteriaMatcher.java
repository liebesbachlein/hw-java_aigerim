package app.space.util.matcher;

import app.space.entity.Entity;

@FunctionalInterface
public interface CriteriaMatcher<T extends Entity> {
    boolean match(T item);
}
