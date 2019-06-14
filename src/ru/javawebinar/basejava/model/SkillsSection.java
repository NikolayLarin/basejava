package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;

/**
 * This class describes ACHIEVEMENT("Достижения") and QUALIFICATIONS("Квалификация") Sections
 * in Resume and stores it as a List of String.
 */
public class SkillsSection extends AbstractSection<List<String>> {

    public SkillsSection(String... skills) {
        this(Arrays.asList(skills));
    }

    public SkillsSection(List<String> element) {
        super(element);
    }
}