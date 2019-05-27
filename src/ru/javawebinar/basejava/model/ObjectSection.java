package ru.javawebinar.basejava.model;

import java.util.ArrayList;

public class ObjectSection extends AbstractSection<String> {

    public ObjectSection(String title, String period, String position, ArrayList<String> description) {
        super(title);
    }

    private String period;
    private String position;
    private ArrayList<String> description;

    public String getPeriod() {
        return period;
    }

    public String getPosition() {
        return position;
    }

    public ArrayList<String> getDescription() {
        return description;
    }
}
