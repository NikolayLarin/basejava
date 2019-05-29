package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.AboutMeSection;
import ru.javawebinar.basejava.model.AbstractSection;
import ru.javawebinar.basejava.model.Career;
import ru.javawebinar.basejava.model.CareerSection;
import ru.javawebinar.basejava.model.ContactSection;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.model.SkillsSection;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * This class tests input in and output out in Resume Sections.
 * Input data described in inner ResumeData class.
 */
public class ResumeTestData {
    public static void main(String[] args) {
        ResumeTestData.ResumeData data = new ResumeTestData().new ResumeData();

        Resume testResume = new Resume("Григорий Кислин");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/YYYY");

        /**
         * It's a input block in Resume Sections
         */
        testResume.setContact(ContactType.PHONE, data.phone);
        testResume.setContact(ContactType.SKYPE, data.skype);
        testResume.setContact(ContactType.EMAIL, data.email);
        testResume.setContact(ContactType.LINKEDIN, data.linkedin);
        testResume.setContact(ContactType.GITHUB, data.gitHub);
        testResume.setContact(ContactType.STACKOVERFLOW, data.stackOverflow);
        testResume.setContact(ContactType.SITE, data.site);

        for (EnumMap.Entry<ContactType, ContactSection> entry : testResume.getContactsMap().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
        }
        printLine();

        testResume.setSection(SectionType.OBJECTIVE, data.objective);
        testResume.setSection(SectionType.PERSONAL, data.personal);
        List<String> achievements = data.getAchievements();
        List<String> qualifications = data.getQualifications();

        testResume.setSection(SectionType.ACHIEVEMENT, achievements);
        testResume.setSection(SectionType.QUALIFICATIONS, qualifications);

        testResume.setSection(SectionType.EXPERIENCE, data.getExperience());
        testResume.setSection(SectionType.EDUCATION, data.getEducation());

        /**
         * First kind of data output from Sections in Resume
         */
        for (EnumMap.Entry<SectionType, AbstractSection> entry : testResume.getSectionsMap().entrySet()) {
            boolean isStringSection = entry.getKey().name().equals("OBJECTIVE") || entry.getKey().name().equals("PERSONAL");
            if (isStringSection) {
                System.out.println(entry.getKey().getTitle() + ": \n" + dot() + entry.getValue().getElement());
                printLine();
            }
        }

        for (EnumMap.Entry<SectionType, AbstractSection> entry : testResume.getSectionsMap().entrySet()) {
            boolean isListSection = entry.getKey().name().equals("ACHIEVEMENT") || entry.getKey().name().equals("QUALIFICATIONS");
            if (isListSection) {
                System.out.println(entry.getKey().getTitle() + ": ");
                SkillsSection skillsSection = (SkillsSection) entry.getValue();
                for (String str : skillsSection.getElement()) {
                    System.out.println(dot() + str);
                }
                printLine();
            }
        }
        printLine();

//        for (EnumMap.Entry<SectionType, AbstractSection> entry : testResume.getSectionsMap().entrySet()) {
//            boolean isCareerSection = entry.getKey().name().equals("EXPERIENCE") || entry.getKey().name().equals("EDUCATION");
//            if (isCareerSection) {
//                System.out.println(entry.getKey().getTitle() + ":");
//                CareerSection careerSection = (CareerSection) entry.getValue();
//
//                System.out.println(careerSection.getElement().getTitle() + "\n" +
//                        careerSection.getElement().getStartDate().format(formatter) + " - " +
//                        careerSection.getElement().getEndDate().format(formatter) + "\n" +
//                        careerSection.getElement().getPosition() + "\n" +
//                        careerSection.getElement().getDescription());
//                printLine();
//            }
//        }

        /**
         * Second kind of data output from Sections in Resume
         */
        for (EnumMap.Entry<SectionType, AboutMeSection> entry : testResume.getAboutMeSectionMap().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": \n" + dot() + entry.getValue().getElement());
            printLine();
        }

        for (EnumMap.Entry<SectionType, SkillsSection> entry : testResume.getSkillsSectionMap().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": ");
            for (String str : entry.getValue().getElement()) {
                System.out.println(dot() + str);
            }
            printLine();
        }
    }

    private static void printLine() {
        System.out.println("-----------------------");
    }

    private static String dot() {
        return "\u00B7 ";
    }

    private class ResumeData {

        String phone = "+7(921) 855-0482";
        String skype = "grigory.kislin";
        String email = "gkislin@yandex.ru";
        String linkedin = "https://www.linkedin.com/in/gkislin";
        String gitHub = "https://github.com/gkislin";
        String stackOverflow = "https://stackoverflow.com/users/548473";
        String site = "http://gkislin.ru/";

        String objective = "Ведущий стажировок " +
                "и корпоративного обучения по Java Web и Enterprise технологиям";
        String personal = "Аналитический склад ума, сильная логика, креативность, " +
                "инициативность. Пурист кода и архитектуры.";

        ArrayList<String> getAchievements() {
            ArrayList<String> achievement = new ArrayList<>();
            achievement.add("С 2013 года: разработка проектов " +
                    "\"Разработка Web приложения\",\"Java Enterprise\", " +
                    "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). " +
                    "Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация " +
                    "онлайн стажировок и ведение проектов. Более 1000 выпускников.");
            achievement.add("Реализация двухфакторной аутентификации для онлайн платформы " +
                    "управления проектами Wrike. Интеграция с Twilio, DuoSecurity, " +
                    "Google Authenticator, Jira, Zendesk.");
            achievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                    "Интеграция с 1С, Bonita BPM, CMIS, LDAP. " +
                    "Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. " +
                    "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
            achievement.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, " +
                    "Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
            achievement.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                    "SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии " +
                    "через систему мониторинга Nagios. Реализация онлайн клиента для администрирования " +
                    "и мониторинга системы по JMX (Jython/ Django).");
            achievement.add("Реализация протоколов по приему платежей всех основных платежных системы России " +
                    "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
            return achievement;
        }

        ArrayList<String> getQualifications() {
            ArrayList<String> qualification = new ArrayList<>();
            qualification.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
            qualification.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
            qualification.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, " +
                    "MySQL, SQLite, MS SQL, HSQLDB ");
            qualification.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy, " +
                    "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
            qualification.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, " +
                    "Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, " +
                    "ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements)");
            qualification.add("Python: Django");
            qualification.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
            qualification.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
            qualification.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, " +
                    "StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, " +
                    "BPMN2, LDAP, OAuth1, OAuth2, JWT");
            qualification.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix, " +
                    "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, " +
                    "Nagios, iReport, OpenCmis, Bonita, pgBouncer");
            qualification.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, " +
                    "архитектурных шаблонов, UML, функционального программирования");
            qualification.add("Родной русский, английский \"upper intermediate\"");
            return qualification;
        }

        ArrayList<Career> getExperience() {
            ArrayList<Career> experience = new ArrayList<>();
            Career career_1 = new Career("Java Online Projects",
                    "Автор проекта",
                    LocalDate.of(2013, 10, 01),
                    LocalDate.now());
            career_1.setDescription("Создание, организация и проведение Java онлайн проектов и стажировок");

            Career career_2 = new Career("Wrike",
                    "Старший разработчик (backend)",
                    LocalDate.of(2014, 10, 01),
                    LocalDate.of(2016, 01, 01));
            career_2.setDescription("Проектирование и разработка онлайн платформы управления проектами Wrike " +
                    "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                    "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
            experience.add(career_1);
            experience.add(career_2);
            return experience;
        }


        ArrayList<Career> getEducation() {
            ArrayList<Career> education = new ArrayList<>();
            Career education_1 = new Career("Coursera",
                    "\"Functional Programming Principles in Scala\" by Martin Odersky",
                    LocalDate.of(2013, 03, 01),
                    LocalDate.of(2013, 05, 01));
            education.add(education_1);
            return education;
        }
    }
}
