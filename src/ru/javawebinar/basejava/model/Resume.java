package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
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
        AboutMeSection aboutMeSection = new AboutMeSection(element);
        this.sectionsMap.put(sectionType, aboutMeSection);
    }

    public void setSection(SectionType sectionType, List<String> element) {
        SkillsSection skillsSection = new SkillsSection(element);
        this.sectionsMap.put(sectionType, skillsSection);
    }

    public void setSection(SectionType sectionType, ArrayList<Career> element) {
        CareerSection careerSection = new CareerSection(element);
        this.sectionsMap.put(sectionType, careerSection);
    }

    public EnumMap<SectionType, AboutMeSection> getAboutMeSectionMap() {
        EnumMap<SectionType, AboutMeSection> aboutMeSectionMap = new EnumMap<>(SectionType.class);
        aboutMeSectionMap.put(SectionType.OBJECTIVE, (AboutMeSection) sectionsMap.get(SectionType.OBJECTIVE));
        aboutMeSectionMap.put(SectionType.PERSONAL, (AboutMeSection) sectionsMap.get(SectionType.PERSONAL));
        return aboutMeSectionMap;
    }

    public EnumMap<SectionType, SkillsSection> getSkillsSectionMap() {
        EnumMap<SectionType, SkillsSection> skillsSectionMap = new EnumMap<>(SectionType.class);
        skillsSectionMap.put(SectionType.ACHIEVEMENT, (SkillsSection) sectionsMap.get(SectionType.ACHIEVEMENT));
        skillsSectionMap.put(SectionType.QUALIFICATIONS, (SkillsSection) sectionsMap.get(SectionType.QUALIFICATIONS));
        return skillsSectionMap;
    }
    public EnumMap<SectionType, CareerSection> getCareerSectionMap() {
        EnumMap<SectionType, CareerSection> careerSectionMap = new EnumMap<>(SectionType.class);
        careerSectionMap.put(SectionType.EXPERIENCE, (CareerSection) sectionsMap.get(SectionType.EXPERIENCE));
        careerSectionMap.put(SectionType.EDUCATION, (CareerSection) sectionsMap.get(SectionType.EDUCATION));
        return careerSectionMap;
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