package ru.javawebinar.basejava.storage.serializer;

import java.io.DataOutputStream;
import java.io.IOException;

@FunctionalInterface
public interface Writer<T> {
    void writeCollection(DataOutputStream dataOutputStream, T collection) throws IOException;
}