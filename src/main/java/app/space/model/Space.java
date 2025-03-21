package main.java.app.space.model;

import main.java.app.space.util.IdGenerator;

public class Space {
    public enum Type {
        OPEN,
        PRIVATE,
        ROOM
    }

    private final int id;
    private String name;
    private Type type;
    private int price;

    public Space(Type type, String name, int price) {
        this.name = name;
        this.id = IdGenerator.generateId();
        this.type = type;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Space{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                '}';
    }
}

