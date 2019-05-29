package ru.javawebinar.basejava.model;

/**
 * This class stores EXPERIENCE("Опыт работы") or EDUCATION("Образование") Sections in Resume
 * as a Career object.
 */
public class CareerSection extends AbstractSection<Career> {

    public CareerSection(Career career) {
        super(career);
    }
}
