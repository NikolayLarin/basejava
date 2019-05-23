package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListSections {
    private Map<ListSectionType, ArrayList<String>> map = new HashMap<>();

    public void setAchievements(ArrayList<String> achievements) {
        map.put(ListSectionType.ACHIEVEMENT, achievements);
    }

    public ArrayList<String> getAchievements() {
        return map.get(ListSectionType.ACHIEVEMENT);
    }

    private enum ListSectionType {
        ACHIEVEMENT("Достижения"),
        QUALIFICATIONS("Квалификация"),
        EXPERIENCE("Опыт работы"),
        EDUCATION("Образование");

        private String title;

        ListSectionType(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}