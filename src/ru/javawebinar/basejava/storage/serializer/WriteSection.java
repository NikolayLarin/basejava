package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.AbstractSection;

import java.io.DataOutputStream;
import java.io.IOException;

@FunctionalInterface
public interface WriteSection {
    void writeSection(DataOutputStream dos, AbstractSection section) throws IOException;
}