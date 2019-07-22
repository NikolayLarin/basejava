package ru.javawebinar.basejava.storage.serializer;

import java.io.DataOutputStream;
import java.io.IOException;

@FunctionalInterface
public interface Writer<T> {
    void writeSection(DataOutputStream dos, T section) throws IOException;
}