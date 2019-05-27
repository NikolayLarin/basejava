package ru.javawebinar.basejava.model;


public class StringSection extends AbstractSection<String> {

    public StringSection(String element) {
        super(element);
    }
    @Override
    public String toString() {
        return getElement();
    }
}
