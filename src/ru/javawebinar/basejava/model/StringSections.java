package ru.javawebinar.basejava.model;


import java.util.HashMap;
import java.util.Map;

public class StringSections {
    private Map<StringSectionType, String> map = new HashMap<>();

    public void setObjective(String objective) {
        map.put(StringSectionType.OBJECTIVE, objective);
    }

    public String getObjective() {
        return map.get(StringSectionType.OBJECTIVE);
    }

    private enum StringSectionType {
        OBJECTIVE("Позиция"),
        PERSONAL("Личные качества");

        private String title;

        StringSectionType(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
