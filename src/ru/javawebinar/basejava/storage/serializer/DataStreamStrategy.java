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

            EnumMap<ContactType, String> contacts = resume.getContacts();
            writeContacts(dos, contacts);

            EnumMap<SectionType, AbstractSection> sections = resume.getSections();

            writeAboutSection(dos, SectionType.OBJECTIVE, sections.get(SectionType.OBJECTIVE));
            writeAboutSection(dos, SectionType.PERSONAL, sections.get(SectionType.PERSONAL));

            writeSkillsSection(dos, SectionType.ACHIEVEMENT, sections.get(SectionType.ACHIEVEMENT));
            writeSkillsSection(dos, SectionType.QUALIFICATIONS, sections.get(SectionType.QUALIFICATIONS));

            writeCareerSection(dos, SectionType.EXPERIENCE, sections.get(SectionType.EXPERIENCE));
            writeCareerSection(dos, SectionType.EDUCATION, sections.get(SectionType.EDUCATION));
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readContacts(dis, resume);

            readAboutSection(dis, resume);
            readAboutSection(dis, resume);

            readSkillsSection(dis, resume);
            readSkillsSection(dis, resume);

            readCareerSection(dis, resume);
            readCareerSection(dis, resume);

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

    private void writeAboutSection(DataOutputStream dos, SectionType sectionType, AbstractSection section) throws IOException {
        AboutSection aboutSection = (AboutSection) section;
        if (aboutSection != null) {
            dos.writeInt(1);
            dos.writeUTF(sectionType.name());
            dos.writeUTF(aboutSection.getElement());
        } else {
            dos.writeInt(0);
        }
    }

    private void readAboutSection(DataInputStream dis, Resume resume) throws IOException {
        if (dis.readInt() == 1) {
            resume.setSection(SectionType.valueOf(dis.readUTF()), new AboutSection(dis.readUTF()));
        }
    }

    private void writeSkillsSection(DataOutputStream dos, SectionType sectionType, AbstractSection section) throws IOException {
        SkillsSection skillsSection = (SkillsSection) section;
        if (skillsSection != null) {
            dos.writeInt(1);
            dos.writeInt(skillsSection.getElement().size());
            for (String str : skillsSection.getElement()) {
                dos.writeUTF(str);
            }
            dos.writeUTF(sectionType.name());
        } else {
            dos.writeInt(0);
        }
    }

    private void readSkillsSection(DataInputStream dis, Resume resume) throws IOException {
        List<String> list = new ArrayList<>();
        if (dis.readInt() == 1) {
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                list.add(dis.readUTF());
            }
            resume.setSection(SectionType.valueOf(dis.readUTF()), new SkillsSection(list));
        }
    }

    private void writeCareerSection(DataOutputStream dos, SectionType sectionType, AbstractSection section) throws IOException {
        CareerSection careerSection = (CareerSection) section;
        if (careerSection != null) {
            dos.writeInt(1);
            dos.writeInt(careerSection.getElement().size());
            for (Career list : careerSection.getElement()) {
                dos.writeUTF(list.getTitle());
                if (list.getUrl() != null) {
                    dos.writeInt(1);
                    dos.writeUTF(list.getUrl());
                } else {
                    dos.writeInt(0);
                }
                dos.writeInt(list.getPositions().size());
                for (Career.Position position : list.getPositions()) {
                    dos.writeUTF(position.getPosition());
                    dos.writeUTF(position.getStartDate().toString());
                    dos.writeUTF(position.getEndDate().toString());
                    if (position.getDescription() != null) {
                        dos.writeInt(1);
                        dos.writeUTF(position.getDescription());
                    } else {
                        dos.writeInt(0);
                    }
                }
            }
            dos.writeUTF(sectionType.name());
        } else {
            dos.writeInt(0);
        }
    }

    private void readCareerSection(DataInputStream dis, Resume resume) throws IOException {
        List<Career> careers = new ArrayList<>();
        if (dis.readInt() == 1) {
            int careersSize = dis.readInt();
            for (int i = 0; i < careersSize; i++) {
                String title = dis.readUTF();
                Career career = new Career(title);
                if (dis.readInt() == 1) {
                    career.setUrl(dis.readUTF());
                }
                int positionsSize = dis.readInt();
                for (int j = 0; j < positionsSize; j++) {
                    Career.Position position = new Career.Position(dis.readUTF(),
                            LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()));
                    if (dis.readInt() == 1) {
                        position.setDescription(dis.readUTF());
                    }
                    career.addPosition(title, position);
                }
                careers.add(career);
            }
            resume.setSection(SectionType.valueOf(dis.readUTF()), new CareerSection(careers));
        }
    }
}