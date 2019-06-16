package ru.javawebinar.basejava.model;

/**
 * This class describes OBJECTIVE("Позиция") and PERSONAL("Личные качества") Sections
 * in Resume and stores it as a String.
 */
public class AboutSection extends AbstractSection<String> {
    private static final long serialVersionUID = 20190616;


    public AboutSection(String element) {
        super(element);
    }

    @Override
    public String toString() {
        return getElement();
    }
}
