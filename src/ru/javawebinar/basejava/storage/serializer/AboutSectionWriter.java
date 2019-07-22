package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.AboutSection;

import java.io.DataOutputStream;
import java.io.IOException;

public class AboutSectionWriter implements Writer<AboutSection> {
    @Override
    public void writeSection(DataOutputStream dos, AboutSection section) throws IOException {
        dos.writeUTF(section.getElement());
    }
}