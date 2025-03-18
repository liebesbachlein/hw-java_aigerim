package app.space.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CustomClassLoader extends java.lang.ClassLoader {
    private String folder = "external";

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> loadedClass = findLoadedClass(name);

        if (loadedClass == null) {
            try {
                loadedClass = super.loadClass(name, false);
            } catch (ClassNotFoundException ex) {
                loadedClass = load(name);
            }
        }

        return loadedClass;
    }

    public Class<?> load(String name) {
        String filepath = folder + File.separator + name + ".class";

        byte[] binaryClassData;
        try(FileInputStream fileInputStream = new FileInputStream(filepath)) {
            binaryClassData = fileInputStream.readAllBytes();
            return defineClass(name, binaryClassData, 0, binaryClassData.length);
        } catch (IOException ex) {
            System.err.println("Cannot read class data from " + folder + "/" + name);
        }

        return null;
    }

}
