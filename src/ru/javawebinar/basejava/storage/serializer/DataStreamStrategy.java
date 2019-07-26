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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

public class DataStreamStrategy implements IOStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Writer<String> stringWriter = dos::writeUTF;

            EnumMap<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (EnumMap.Entry<ContactType, String> entry : contacts.entrySet()) {
                List<String> contactsList = Arrays.asList(entry.getKey().name(), entry.getValue());
                writeWithException(contactsList, stringWriter);
            }

            Writer<Career> careerWriter = element -> {
                dos.writeUTF(element.getTitle());
                String url = element.getUrl();
                dos.writeUTF(url != null ? url : "");
                List<Career.Position> positions = element.getPositions();
                dos.writeInt(positions.size());
                for (Career.Position position : positions) {
                    dos.writeUTF(position.getPosition());
                    dos.writeUTF(position.getStartDate().toString());
                    dos.writeUTF(position.getEndDate().toString());
                    String description = position.getDescription();
                    dos.writeUTF(description != null ? description : "");
                }
            };

            EnumMap<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (EnumMap.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                String sectionTypeName = entry.getKey().name();
                dos.writeUTF(sectionTypeName);
                AbstractSection section = entry.getValue();
                switch (sectionTypeName) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        List<String> aboutList = Collections.singletonList(((AboutSection) section).getElement());
                        writeWithException(aboutList, stringWriter);
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        List<String> skillsList = ((SkillsSection) section).getElement();
                        dos.writeInt(skillsList.size());
                        writeWithException(skillsList, stringWriter);
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        List<Career> careerList = ((CareerSection) section).getElement();
                        dos.writeInt(careerList.size());
                        writeWithException(careerList, careerWriter);
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();

            Resume resume = new Resume(uuid, fullName);

            readContacts(dis, resume);
            readSections(dis, resume);
            return resume;
        }
    }

    private <T> void writeWithException(Collection<T> collection, Writer<? super T> writer) throws IOException {
        for (T element : collection) {
            writer.writeElement(element);
        }
    }

    private void readContacts(DataInputStream dis, Resume resume) throws IOException {
        int contactsSize = dis.readInt();
        for (int i = 0; i < contactsSize; i++) {
            resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
    }

    private void readSections(DataInputStream dis, Resume resume) throws IOException {
        int sectionsSize = dis.readInt();
        for (int i = 0; i < sectionsSize; i++) {
            String sectionType = dis.readUTF();
            switch (sectionType) {
                case "OBJECTIVE":
                case "PERSONAL":
                    readAboutSection(dis, sectionType, resume);
                    break;
                case "ACHIEVEMENT":
                case "QUALIFICATIONS":
                    readSkillsSection(dis, sectionType, resume);
                    break;
                case "EXPERIENCE":
                case "EDUCATION":
                    readCareerSection(dis, sectionType, resume);
                    break;
            }
        }
    }

    private void readAboutSection(DataInputStream dis, String sectionType, Resume resume) throws IOException {
        resume.setSection(SectionType.valueOf(sectionType), new AboutSection(dis.readUTF()));
    }

    private void readSkillsSection(DataInputStream dis, String sectionType, Resume resume) throws IOException {
        List<String> list = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            list.add(dis.readUTF());
        }
        resume.setSection(SectionType.valueOf(sectionType), new SkillsSection(list));
    }

    private void readCareerSection(DataInputStream dis, String sectionType, Resume resume) throws IOException {
        List<Career> careers = new ArrayList<>();
        int careersSize = dis.readInt();
        for (int i = 0; i < careersSize; i++) {
            String title = dis.readUTF();
            Career career = new Career(title);
            String url = dis.readUTF();
            career.setUrl(!url.equals("") ? url : null);
            int positionsSize = dis.readInt();
            for (int j = 0; j < positionsSize; j++) {
                Career.Position position = new Career.Position(dis.readUTF(),
                        LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()));
                String description = dis.readUTF();
                position.setDescription(!description.equals("") ? description : null);
                career.addPosition(title, position);
            }
            careers.add(career);
        }
        resume.setSection(SectionType.valueOf(sectionType), new CareerSection(careers));
    }
}