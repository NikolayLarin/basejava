package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class stores EXPERIENCE("Опыт работы") or EDUCATION("Образование") Sections in Resume
 * as a Career object.
 */
public class CareerSection extends AbstractSection<ArrayList<Career>> {

    public CareerSection(ArrayList<Career> career) {
        super(career);
    }
}
