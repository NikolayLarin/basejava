package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;

public class ResumeTestData {
    public static void main(String[] args) {

        ResumeTestData.ResumeData data = new ResumeTestData().new ResumeData();

        Resume testResume = new Resume("Григорий Кислин");

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
        ArrayList<String> achievements = new ArrayList<>(Arrays.asList(data.getAchievements()));
        ArrayList<String> qualifications = new ArrayList<>(Arrays.asList(data.getQualifications()));
        testResume.setSection(SectionType.ACHIEVEMENT, achievements);
        testResume.setSection(SectionType.QUALIFICATIONS, qualifications);
//        for (EnumMap.Entry<SectionType, AbstractSection> entry : testResume.getSectionsMap().entrySet()) {
//            System.out.println(entry.getKey().getTitle() + ": \n" + dot() + entry.getValue().getElement());
//            printLine();
//        }

        for (EnumMap.Entry<SectionType, AbstractSection> entryTitles : testResume.getSectionsMap().entrySet()) {
            System.out.println(entryTitles.getKey().getTitle() + ": ");
            for (EnumMap.Entry<SectionType, AbstractSection> entryVolumes : testResume.getSectionsMap().entrySet()) {
                System.out.println(dot() + entryVolumes.getValue().getElement());
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

        String[] getAchievements() {
            String[] achievement = new String[6];
            achievement[0] = "С 2013 года: разработка проектов " +
                    "\"Разработка Web приложения\",\"Java Enterprise\", " +
                    "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). " +
                    "Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация " +
                    "онлайн стажировок и ведение проектов. Более 1000 выпускников.";
            achievement[1] = "Реализация двухфакторной аутентификации для онлайн платформы " +
                    "управления проектами Wrike. Интеграция с Twilio, DuoSecurity, " +
                    "Google Authenticator, Jira, Zendesk.";
            achievement[2] = "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                    "Интеграция с 1С, Bonita BPM, CMIS, LDAP. " +
                    "Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. " +
                    "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.";
            achievement[3] = "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, " +
                    "Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга. ";
            achievement[4] = "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                    "SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии " +
                    "через систему мониторинга Nagios. Реализация онлайн клиента для администрирования " +
                    "и мониторинга системы по JMX (Jython/ Django).";
            achievement[5] = "Реализация протоколов по приему платежей всех основных платежных системы России " +
                    "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.";
            return achievement;
        }

        String[] getQualifications() {
            String[] qualification = new String[12];
            qualification[0] = "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2";
            qualification[1] = "Version control: Subversion, Git, Mercury, ClearCase, Perforce";
            qualification[2] = "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, " +
                    "MySQL, SQLite, MS SQL, HSQLDB ";
            qualification[3] = "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy, " +
                    "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts";
            qualification[4] = "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, " +
                    "Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, " +
                    "ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements)";
            qualification[5] = "Python: Django";
            qualification[6] = "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js";
            qualification[7] = "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka";
            qualification[8] = "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, " +
                    "StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, " +
                    "BPMN2, LDAP, OAuth1, OAuth2, JWT";
            qualification[9] = "Инструменты: Maven + plugin development, Gradle, настройка Ngnix, " +
                    "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, " +
                    "Nagios, iReport, OpenCmis, Bonita, pgBouncer";
            qualification[10] = "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, " +
                    "архитектурных шаблонов, UML, функционального программирования";
            qualification[11] = "Родной русский, английский \"upper intermediate\"";
            return qualification;
        }

        void experience_1() {
            String element = "Java Online Projects";
            String period = "10/2013 - Сейчас";
            String position = "Автор проекта";
            ArrayList<String> description = new ArrayList<>();
            description.add("Создание, организация и проведение Java онлайн проектов и стажировок");
        }

        void education_1() {
            String element = "Coursera";
            String period = "03/2013 - 05/2013";
            String position = "\"Functional Programming Principles in Scala\" by Martin Odersky";
        }
    }
}
