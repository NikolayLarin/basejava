package ru.javawebinar.basejava.model;

public abstract class AbstractSection<T> {
    private T element;

    public AbstractSection(T element) {
        this.element = element;
    }

    public T getElement() {
        return element;
    }
}