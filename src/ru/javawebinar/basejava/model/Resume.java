package ru.javawebinar.basejava.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private String fullName;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid can't be null");
        Objects.requireNonNull(fullName, "fullName can't be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.getFullName());
        return cmp != 0 ? cmp : uuid.compareTo(o.getUuid());
    }

    private class Contacts {
        private Map<ContactType, String> contacts = new HashMap<>();

        public void setName(String name) {
            contacts.put(ContactType.NAME, name);
        }

        public String getName() {
            return contacts.get(ContactType.NAME);
        }
    }

    private enum ContactType {
        NAME,
        SURNAME,
        ADDRESS,
        EMAIL;
    }

    private class Sections {
        private Map<SectionType, String> sections = new HashMap<>();
    }

    private enum SectionType {
        PERSONAL("Личные качества"),
        OBJECTIVE("Позиция"),
        ACHIEVEMENT("Достижения"),
        QUALIFICATIONS("Квалификация"),
        EXPERIENCE("Опыт работы"),
        EDUCATION("Образование");

        private String title;

        SectionType(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}