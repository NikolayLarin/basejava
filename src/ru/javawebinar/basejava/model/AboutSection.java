package ru.javawebinar.basejava.model;

import java.util.Objects;

/**
 * This class describes OBJECTIVE("Позиция") and PERSONAL("Личные качества") Sections
 * in Resume and stores it as a String.
 */
public class AboutSection extends AbstractSection {
    private static final long serialVersionUID = 20190616;

    private String element;

    public AboutSection() {
    }

    public AboutSection(String element) {
        Objects.requireNonNull(element, "About section element can't be null");
        this.element = element;
    }

    public String getElement() {
        return element;
    }

    @Override
    public String toString() {
        return element;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AboutSection that = (AboutSection) o;
        return Objects.equals(element, that.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element);
    }
}