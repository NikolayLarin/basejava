package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Map;

/**
 * This class stores EXPERIENCE("Опыт работы") or EDUCATION("Образование") Sections in Resume
 * as a List of Career object.
 */
public class CareerSection extends Section<Map<Link, List<Career>>> {

    public CareerSection(Map<Link, List<Career>> career) {
        super(career);
    }
}