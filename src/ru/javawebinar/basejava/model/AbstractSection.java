package ru.javawebinar.basejava.model;

import java.util.EnumMap;

abstract class AbstractSection<T> {

    EnumMap<SectionType, T> map = new EnumMap<>(SectionType.class);

    void setObjective(T objective) {
        map.put(SectionType.OBJECTIVE, objective);
    }

    void setPersonal(T personal) {
        map.put(SectionType.PERSONAL, personal);
    }

    void setAchievements(T achievements) {
        map.put(SectionType.ACHIEVEMENT, achievements);
    }

    void setQualifications(T qualifications) {
        map.put(SectionType.QUALIFICATIONS, qualifications);
    }

    void setEducations(T educations) {
        map.put(SectionType.EDUCATION, educations);
    }


    EnumMap<SectionType, T> getMap() {
        return map;
    }

}