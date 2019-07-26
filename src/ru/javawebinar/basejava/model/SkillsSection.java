package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class describes ACHIEVEMENT ("Достижения") and QUALIFICATIONS ("Квалификация") Sections
 * in Resume and stores it as a List of Strings.
 */
public class SkillsSection extends AbstractSection {
    private static final long serialVersionUID = 20190616;

    private List<String> element;

    public SkillsSection() {
    }

    public SkillsSection(String... skills) {
        this(Arrays.asList(skills));
    }

    public SkillsSection(List<String> element) {
        Objects.requireNonNull(element, "Skills section element can't be null");
        this.element = element;
    }

    public List<String> getElement() {
        return element;
    }

    @Override
    public String toString() {
        return element.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillsSection that = (SkillsSection) o;
        return Objects.equals(element, that.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element);
    }
}