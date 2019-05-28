package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Objects;
import java.util.UUID;

/**
 * Resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private String fullName;
    private EnumMap<ContactType, ContactSection> contactsMap = new EnumMap<>(ContactType.class);
    private EnumMap<SectionType, AbstractSection> sectionsMap = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid can't be null");
        Objects.requireNonNull(fullName, "fullName can't be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void setContact(ContactType contactType, String contact) {
        ContactSection contactSection = new ContactSection(contact);
        this.contactsMap.put(contactType, contactSection);
    }

    public void setSection(SectionType sectionType, String element) {
        StringSection stringSection = new StringSection(element);
        this.sectionsMap.put(sectionType, stringSection);
    }

    public void setSection(SectionType sectionType, ArrayList<String> element) {
        ListSection listSection = new ListSection(element);
        this.sectionsMap.put(sectionType, listSection);
    }

    public EnumMap<ContactType, ContactSection> getContactsMap() {
        return new EnumMap<>(contactsMap);
    }

    public EnumMap<SectionType, AbstractSection> getSectionsMap() {
        return new EnumMap<>(sectionsMap);
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
        return uuid.equals(resume.uuid) &&
                fullName.equals(resume.fullName) &&
                contactsMap.equals(resume.contactsMap) &&
                sectionsMap.equals(resume.sectionsMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contactsMap, sectionsMap);
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
}