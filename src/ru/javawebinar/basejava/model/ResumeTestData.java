package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;

public class ResumeTestData {
    public static void main(String[] args) {

        ResumeData data = new ResumeData();

        Resume testResume = new Resume("Григорий Кислин");

        testResume.contacts.setPhone(data.phone);
        testResume.contacts.setSkype(data.skype);
        testResume.contacts.setEmail(data.email);
        testResume.contacts.setLinkedin(data.linkedin);
        testResume.contacts.setGitHub(data.gitHub);
        testResume.contacts.setStackOverflow(data.stackOverflow);
        testResume.contacts.setSite(data.site);

        for (EnumMap.Entry<ContactType, String> entry : testResume.contacts.getMap().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
        }
        printLine();

        testResume.stringSections.setObjective(data.objective);
        testResume.stringSections.setPersonal(data.personal);
        for (EnumMap.Entry<SectionType, String> entry : testResume.stringSections.getMap().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": \n" + dot() + entry.getValue());
            printLine();
        }

        ArrayList<String> achievements = new ArrayList<>(Arrays.asList(data.getAchievements()));
        testResume.listSections.setAchievements(achievements);
        ArrayList<String> qualifications = new ArrayList<>(Arrays.asList(data.getQualifications()));
        testResume.listSections.setQualifications(qualifications);
        for (EnumMap.Entry<SectionType, ArrayList<String>> entry : testResume.listSections.getMap().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": ");
            for (int i = 0; i < entry.getValue().size(); i++) {
                System.out.println(dot() + entry.getValue().get(i));
            }
        }
        printLine();



    }

    private static void printLine() {
        System.out.println("-----------------------");
    }

    private static String dot() {
        return "\u00B7 ";
    }
}
