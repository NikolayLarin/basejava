package ru.javawebinar.basejava.model;

import java.util.Objects;

public abstract class Section<T> {
    private final T element;

    public Section(T element) {
        Objects.requireNonNull(element, " sections element can't be null");
        this.element = element;
    }

    public T getElement() {
        return element;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section<?> section = (Section<?>) o;
        return element.equals(section.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element);
    }
}