package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.AboutSection;
import ru.javawebinar.basejava.model.AbstractSection;

import java.io.DataOutputStream;
import java.io.IOException;

public class WriteAboutSection implements WriteSection {

    @Override
    public void writeSection(DataOutputStream dos, AbstractSection section) {
        try {
            dos.writeUTF(((AboutSection) section).getElement());
        } catch (IOException e) {
            throw new StorageException("Can't write data", e);
        }
    }
}