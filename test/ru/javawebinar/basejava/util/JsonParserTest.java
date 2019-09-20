package ru.javawebinar.basejava.util;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.model.AbstractSection;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;

import static ru.javawebinar.basejava.storage.AbstractStorageTest.RESUME_1;

public class JsonParserTest {

    @Test
    public void testResume() throws Exception {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_1, resume);
    }

    @Test
    public void write() throws Exception {
        AbstractSection section = RESUME_1.getSections().get(SectionType.valueOf("OBJECTIVE"));
        String json = JsonParser.write(section, AbstractSection.class);
        System.out.println(json);
        AbstractSection actual = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(section, actual);
    }
}