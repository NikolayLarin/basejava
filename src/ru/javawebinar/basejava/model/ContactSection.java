package ru.javawebinar.basejava.model;

import java.util.Objects;

public class ContactSection {
    private String contact;

    protected ContactSection(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactSection that = (ContactSection) o;
        return contact.equals(that.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contact);
    }
}


