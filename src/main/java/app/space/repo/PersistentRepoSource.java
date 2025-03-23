package app.space.repo;
import app.space.entity.Entity;
import app.space.util.PersistenceException;
import lombok.Getter;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Getter
public class PersistentRepoSource<T extends Entity> implements RepoSource<T> {
    private final Map<Integer, T> entityMap;
    private final String FILE_STORAGE ;

    public PersistentRepoSource(Class entityClass) throws PersistenceException {
        FILE_STORAGE ="./src/main/resources/" + entityClass.getName();
        entityMap = new HashMap<>();
        initPersistence();
    }

    private void initPersistence() throws PersistenceException {
        File file = new File(FILE_STORAGE);

        if (!file.exists() || !file.isFile()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                throw new PersistenceException(this.getClass(), ex.getMessage());
            }
        }

        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_STORAGE))) {
            Map<Integer, T> data = (Map<Integer, T>) objectInputStream.readObject();
            entityMap.putAll(data);
        } catch(EOFException ex) {
            persist();
        } catch (IOException | ClassNotFoundException ex) {
            throw new PersistenceException(this.getClass(), ex.getMessage());
        }
    }

    private void persist() throws PersistenceException {
        try(ObjectOutputStream objectOutStream = new ObjectOutputStream(new FileOutputStream(FILE_STORAGE))) {
            objectOutStream.writeObject(entityMap);
        } catch (IOException ex) {
            throw new PersistenceException(this.getClass(), ex.getMessage());
        }
    }

    public void close() {
        try {
            persist();
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
