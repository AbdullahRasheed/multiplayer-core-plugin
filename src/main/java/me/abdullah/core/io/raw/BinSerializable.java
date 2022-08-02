package me.abdullah.core.io.raw;

import java.io.*;

public class BinSerializable implements Serializable {

    // TODO make separate classes for java serialization and kryo serialization

    @Override
    public void store(Object o, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(o);

        oos.close();
        fos.close();
    }

    @Override
    public Object read(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);

        Object obj = ois.readObject();

        ois.close();
        fis.close();

        return obj;
    }
}
