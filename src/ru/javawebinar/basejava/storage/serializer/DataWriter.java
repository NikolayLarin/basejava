package ru.javawebinar.basejava.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface DataWriter<T> {
    void writeElement(T element) throws IOException;
}