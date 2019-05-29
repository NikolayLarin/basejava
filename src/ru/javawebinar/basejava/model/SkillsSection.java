package ru.javawebinar.basejava.model;

import java.util.ArrayList;
/**
 * This class describes ACHIEVEMENT("Достижения") and QUALIFICATIONS("Квалификация") Sections
 * in Resume and stores it as a ArrayList<String>.
 */
public class SkillsSection extends AbstractSection<ArrayList<String>> {

    public SkillsSection(ArrayList<String> element) {
        super(element);
    }
}