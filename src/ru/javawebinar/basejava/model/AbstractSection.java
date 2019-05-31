package ru.javawebinar.basejava.model;

import java.util.Objects;

public abstract class AbstractSection<T> {
    private final T element;

    public AbstractSection(T element) {
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
        AbstractSection<?> section = (AbstractSection<?>) o;
        return element.equals(section.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element);
    }
}