package ru.javawebinar.basejava.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface Writer<T> {
    void writeElement(T element) throws IOException;
}