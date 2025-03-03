package repo;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PersistentRepo<T> {
        protected final Map<Integer, T> idToItem;
        private boolean isPersistable;
        private final String FILE_STORAGE;

        public PersistentRepo(String fileStorageName) {
                idToItem = new HashMap<>();
                FILE_STORAGE = fileStorageName;
        }

        public boolean init() {
                try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_STORAGE))) {
                        Map<Integer, T> data = (Map<Integer, T>) objectInputStream.readObject();
                        idToItem.putAll(data);
                        isPersistable = true;
                        return true;
                } catch(EOFException ex) {
                        if (saveState()) {
                                isPersistable = true;
                                return true;
                        };
                } catch (IOException | ClassNotFoundException ex) {
                        System.out.println("Error occurred while retrieving items from "
                                + FILE_STORAGE + ". "
                                + ex.getMessage());
                }

                isPersistable = false;
                return false;
        }

        private boolean saveState() {
                try(ObjectOutputStream objectOutStream = new ObjectOutputStream(new FileOutputStream(FILE_STORAGE))) {
                        objectOutStream.writeObject(idToItem);
                        return true;
                } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                }

                return false;
        }

        public boolean persist() {
                if(isPersistable) return saveState();
                return false;
        }


        public void disablePersistence() {
                isPersistable = false;
        }
}
