package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;

/**
 * This class stores EXPERIENCE("Опыт работы") or EDUCATION("Образование") Sections in Resume
 * as a List of Position object.
 */
public class CareerSection extends AbstractSection<List<Career>> {
    private static final long serialVersionUID = 20190616;


    public CareerSection(Career... careers) {
        this(Arrays.asList(careers));
    }

    public CareerSection(List<Career> element) {
        super(element);
    }
}