package ru.javawebinar.basejava.model;

import java.util.Map;
import java.util.Set;

/**
 * This class stores EXPERIENCE("Опыт работы") or EDUCATION("Образование") Sections in Resume
 * as a List of Career object.
 */
public class CareerSection extends Section<Map<Link, Set<Career>>> {

    public CareerSection(Map<Link, Set<Career>> career) {
        super(career);
    }
}