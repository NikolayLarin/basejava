package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.AboutSection;
import ru.javawebinar.basejava.model.Career;
import ru.javawebinar.basejava.model.CareerSection;
import ru.javawebinar.basejava.model.Link;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SkillsSection;
import ru.javawebinar.basejava.util.XmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class XmlStreamStrategy implements IOStrategy{
    private XmlParser xmlParser;

    public XmlStreamStrategy() {
        xmlParser = new XmlParser(
                Resume.class, Career.class, Link.class, CareerSection.class,
                AboutSection.class, SkillsSection.class, Career.Position.class);
    }

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
        }

    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)){
            return xmlParser.unmarshall(reader);
        }
    }
}