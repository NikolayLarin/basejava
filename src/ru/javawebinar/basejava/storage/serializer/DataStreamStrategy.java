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
import java.util.EnumMap;
import java.util.List;

public class DataStreamStrategy implements IOStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            writeContacts(dos, resume.getContacts());
            writeSections(dos, resume.getSections());
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

    private void writeContacts(DataOutputStream dos, EnumMap<ContactType, String> contacts) throws IOException {
        dos.writeInt(contacts.size());
        for (EnumMap.Entry<ContactType, String> entry : contacts.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        }
    }

    private void readContacts(DataInputStream dis, Resume resume) throws IOException {
        int contactsSize = dis.readInt();
        for (int i = 0; i < contactsSize; i++) {
            resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
    }

    private void writeSections(DataOutputStream dos, EnumMap<SectionType, AbstractSection> sections) throws IOException {
        dos.writeInt(sections.size());
        for (EnumMap.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
            SectionType sectionType = entry.getKey();
            AbstractSection section = entry.getValue();
            switch (sectionType.name()) {
                case "OBJECTIVE":
                case "PERSONAL":
                    writeAboutSection(dos, sectionType, section);
                    break;
                case "ACHIEVEMENT":
                case "QUALIFICATIONS":
                    writeSkillsSection(dos, sectionType, section);
                    break;
                case "EXPERIENCE":
                case "EDUCATION":
                    writeCareerSection(dos, sectionType, section);
                    break;
            }
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

    private void writeAboutSection(DataOutputStream dos, SectionType sectionType, AbstractSection section) throws IOException {
        dos.writeUTF(sectionType.name());
        dos.writeUTF(((AboutSection) section).getElement());
    }

    private void readAboutSection(DataInputStream dis, String sectionType, Resume resume) throws IOException {
        resume.setSection(SectionType.valueOf(sectionType), new AboutSection(dis.readUTF()));
    }

    private void writeSkillsSection(DataOutputStream dos, SectionType sectionType, AbstractSection section) throws IOException {
        dos.writeUTF(sectionType.name());
        SkillsSection skillsSection = (SkillsSection) section;
        List<String> element = skillsSection.getElement();
        dos.writeInt(element.size());
        for (String str : element) {
            dos.writeUTF(str);
        }
    }

    private void readSkillsSection(DataInputStream dis, String sectionType, Resume resume) throws IOException {
        List<String> list = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            list.add(dis.readUTF());
        }
        resume.setSection(SectionType.valueOf(sectionType), new SkillsSection(list));
    }

    private void writeCareerSection(DataOutputStream dos, SectionType sectionType, AbstractSection section) throws IOException {
        dos.writeUTF(sectionType.name());
        CareerSection careerSection = (CareerSection) section;
        List<Career> element = careerSection.getElement();
        dos.writeInt(element.size());
        for (Career list : element) {
            dos.writeUTF(list.getTitle());
            if (list.getUrl() != null) {
                dos.writeUTF(list.getUrl());
            } else {
                dos.writeUTF("");
            }
            List<Career.Position> positions = list.getPositions();
            dos.writeInt(positions.size());
            for (Career.Position position : positions) {
                dos.writeUTF(position.getPosition());
                dos.writeUTF(position.getStartDate().toString());
                dos.writeUTF(position.getEndDate().toString());
                if (position.getDescription() != null) {
                    dos.writeUTF(position.getDescription());
                } else {
                    dos.writeUTF("");
                }
            }
        }
    }

    private void readCareerSection(DataInputStream dis, String sectionType, Resume resume) throws IOException {
        List<Career> careers = new ArrayList<>();
        int careersSize = dis.readInt();
        for (int i = 0; i < careersSize; i++) {
            String title = dis.readUTF();
            Career career = new Career(title);
            String url = dis.readUTF();
            if (!url.equals("")) {
                career.setUrl(url);
            }
            int positionsSize = dis.readInt();
            for (int j = 0; j < positionsSize; j++) {
                Career.Position position = new Career.Position(dis.readUTF(),
                        LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()));
                String description = dis.readUTF();
                if (!description.equals("")) {
                    position.setDescription(description);
                }
                career.addPosition(title, position);
            }
            careers.add(career);
        }
        resume.setSection(SectionType.valueOf(sectionType), new CareerSection(careers));
    }
}