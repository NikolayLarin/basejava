package ru.javawebinar.basejava.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface DataReader {
    void readElement() throws IOException;
}