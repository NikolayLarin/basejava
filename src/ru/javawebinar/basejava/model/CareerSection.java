package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class stores EXPERIENCE("Опыт работы") or EDUCATION("Образование") Sections in Resume
 * as a Career object.
 */
public class CareerSection extends AbstractSection<List<Career>> {

    public CareerSection(List<Career> career) {
        super(career);
    }
}
