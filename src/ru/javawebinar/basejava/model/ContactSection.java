package ru.javawebinar.basejava.model;


public class ContactSection {
    private String contact;

    public ContactSection(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return contact;
    }
}


