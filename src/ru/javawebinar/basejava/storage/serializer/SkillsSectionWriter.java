package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.SkillsSection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class SkillsSectionWriter implements Writer<SkillsSection> {
    @Override
    public void writeSection(DataOutputStream dos, SkillsSection section) throws IOException {
        List<String> element = section.getElement();
        dos.writeInt(element.size());
        for (String str : element) {
            dos.writeUTF(str);
        }
    }
}