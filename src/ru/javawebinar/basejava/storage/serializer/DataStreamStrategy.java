package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.AboutSection;
import ru.javawebinar.basejava.model.AbstractSection;
import ru.javawebinar.basejava.model.Career;
import ru.javawebinar.basejava.model.CareerSection;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.model.SkillsSection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamStrategy implements IOStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            writeWithException(dos, resume.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeWithException(dos, resume.getSections().entrySet(), entry -> {
                String sectionTypeName = entry.getKey().name();
                dos.writeUTF(sectionTypeName);
                AbstractSection section = entry.getValue();
                switch (sectionTypeName) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        dos.writeUTF(((AboutSection) section).getElement());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        writeWithException(dos, ((SkillsSection) section).getElement(), dos::writeUTF);
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        writeWithException(dos, ((CareerSection) section).getElement(), element -> {
                            dos.writeUTF(element.getTitle());
                            String url = element.getUrl();
                            dos.writeUTF(url != null ? url : "");
                            writeWithException(dos, element.getPositions(), position -> {
                                dos.writeUTF(position.getPosition());
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                String description = position.getDescription();
                                dos.writeUTF(description != null ? description : "");
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            readWithException(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readWithException(dis, () -> {
                String sectionType = dis.readUTF();
                switch (sectionType) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        resume.setSection(SectionType.valueOf(sectionType), new AboutSection(dis.readUTF()));
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        List<String> list = new ArrayList<>();
                        readWithException(dis, () -> list.add(dis.readUTF()));
                        resume.setSection(SectionType.valueOf(sectionType), new SkillsSection(list));

                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        List<Career> careers = new ArrayList<>();
                        readWithException(dis, () -> {
                            String title = dis.readUTF();
                            Career career = new Career(title);
                            String url = dis.readUTF();
                            career.setUrl(!url.equals("") ? url : null);
                            readWithException(dis, () -> {
                                Career.Position position = new Career.Position(dis.readUTF(),
                                        LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()));
                                String description = dis.readUTF();
                                position.setDescription(!description.equals("") ? description : null);
                                career.addPosition(title, position);
                            });
                            careers.add(career);
                        });
                        resume.setSection(SectionType.valueOf(sectionType), new CareerSection(careers));
                        break;
                }
            });
            return resume;
        }
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, DataWriter<? super T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.writeElement(element);
        }
    }

    private void readWithException(DataInputStream dis, DataReader reader) throws IOException {
        int collectionSize = dis.readInt();
        for (int i = 0; i < collectionSize; i++) {
            reader.readElement();
        }
    }
}