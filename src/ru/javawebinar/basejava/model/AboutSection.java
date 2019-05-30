package ru.javawebinar.basejava.model;

/**
 * This class describes OBJECTIVE("Позиция") and PERSONAL("Личные качества") Sections
 * in Resume and stores it as a String.
 */
public class AboutSection extends AbstractSection<String> {

    public AboutSection(String element) {
        super(element);
    }

    @Override
    public String toString() {
        return getElement();
    }
}
