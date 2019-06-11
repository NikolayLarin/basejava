package ru.javawebinar.basejava.model;

import java.util.List;

/**
 * This class stores EXPERIENCE("Опыт работы") or EDUCATION("Образование") Sections in Resume
 * as a List of Career object.
 */
public class CareerSection extends AbstractSection<List<CareerList>> {

    public CareerSection(List<CareerList> careerList) {
        super(careerList);
    }
}