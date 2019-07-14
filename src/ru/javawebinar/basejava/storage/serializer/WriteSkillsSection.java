package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.AbstractSection;
import ru.javawebinar.basejava.model.SkillsSection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class WriteSkillsSection implements WriteSection {

    @Override
    public void writeSection(DataOutputStream dos, AbstractSection section) {
        try {
            List<String> element = ((SkillsSection) section).getElement();
            dos.writeInt(element.size());
            for (String str : element) {
                dos.writeUTF(str);
            }
        } catch (IOException e) {
            throw new StorageException("Can't write data", e);
        }
    }
}