package ru.javawebinar.basejava.model;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume testResume = new Resume("testResume");

        testResume.contacts.setName("Григорий");
        System.out.println(testResume.contacts.getName());

        testResume.stringSections.setObjective("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        System.out.println(testResume.stringSections.getObjective());

    }
}
