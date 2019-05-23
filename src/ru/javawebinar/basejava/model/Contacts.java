package ru.javawebinar.basejava.model;


import java.util.HashMap;
import java.util.Map;

public class Contacts {
    private Map<ContactType, String> map = new HashMap<>();

    public void setName(String name) {
        map.put(ContactType.NAME, name);
    }

    public String getName() {
        return map.get(ContactType.NAME);
    }

    private enum ContactType {
        NAME,
        SURNAME,
        ADDRESS,
        EMAIL;
    }
}


