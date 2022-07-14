package me.abdullah.core.io.raw;

import java.io.*;

public class BinSerializable {

    private Serializable serializable;
    public BinSerializable(Serializable serializable){
        this.serializable = serializable;
    }

    public void storeTo(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(serializable);

        oos.close();
        fos.close();
    }

    public static Object read(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);

        Object obj = ois.readObject();

        ois.close();
        fis.close();

        return obj;
    }
}
