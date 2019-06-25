package ru.javawebinar.basejava.storage;

public class DeveloperRunner {
    public static void main(String[] args) {
        Developer developer = new Developer();

        developer.setActivity(new Reading());
        developer.executeActivity();
    }
}
