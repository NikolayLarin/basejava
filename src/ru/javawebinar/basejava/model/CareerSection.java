package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class stores EXPERIENCE("Опыт работы") or EDUCATION("Образование") Sections in Resume
 * as a List of Position object.
 */
public class CareerSection extends AbstractSection {
    private static final long serialVersionUID = 20190616;

    private List<Career> element;

    public CareerSection() {
    }

    public CareerSection(Career... careers) {
        this(Arrays.asList(careers));
    }

    public CareerSection(List<Career> element) {
        Objects.requireNonNull(element, "Career section element can't be null");
        this.element = element;
    }

    public List<Career> getElement() {
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
        CareerSection that = (CareerSection) o;
        return Objects.equals(element, that.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element);
    }
}