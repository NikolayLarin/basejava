package ru.javawebinar.basejava.model;


import java.util.EnumMap;

public class ContactSection {
    private EnumMap<ContactType, String> map = new EnumMap<>(ContactType.class);

    protected void setPhone(String name) {
        map.put(ContactType.PHONE, name);
    }

    protected void setSkype(String name) {
        map.put(ContactType.SKYPE, name);
    }

    protected void setEmail(String name) {
        map.put(ContactType.EMAIL, name);
    }

    protected void setLinkedin(String name) {
        map.put(ContactType.LINKEDIN, name);
    }

    protected void setGitHub(String name) {
        map.put(ContactType.GITHUB, name);
    }

    protected void setStackOverflow(String name) {
        map.put(ContactType.STACKOVERFLOW, name);
    }

    protected void setSite(String name) {
        map.put(ContactType.SITE, name);
    }

    protected EnumMap<ContactType, String> getMap() {
        return map;
    }
}


