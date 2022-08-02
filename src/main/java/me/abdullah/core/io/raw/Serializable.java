package me.abdullah.core.io.raw;

import java.io.File;
import java.io.IOException;

public interface Serializable<T> {

    void store(T t, File file) throws IOException;

    T read(File file) throws IOException, ClassNotFoundException;
}
