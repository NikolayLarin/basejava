package ru.javawebinar.basejava;

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

        for (EnumMap.Entry<SectionType, AbstractSection> entry : testResume.getSectionsMap().entrySet()) {
            boolean isAboutSection = entry.getKey().name().equals("OBJECTIVE") || entry.getKey().name().equals("PERSONAL");
            if (isAboutSection) {
                System.out.println(entry.getKey().getTitle() + ": \n" + dot() + entry.getValue().getElement());
                printLine();
            }
        }

        for (EnumMap.Entry<SectionType, AbstractSection> entry : testResume.getSectionsMap().entrySet()) {
            boolean isSkillsSection = entry.getKey().name().equals("ACHIEVEMENT") || entry.getKey().name().equals("QUALIFICATIONS");
            if (isSkillsSection) {
                System.out.println(entry.getKey().getTitle() + ": ");
                SkillsSection skillsSection = (SkillsSection) entry.getValue();
                for (String str : skillsSection.getElement()) {
                    System.out.println(dot() + str);
                }
                printLine();
            }
        }

        for (EnumMap.Entry<SectionType, AbstractSection> entry : testResume.getSectionsMap().entrySet()) {
            boolean isCareerSection = entry.getKey().name().equals("EXPERIENCE") || entry.getKey().name().equals("EDUCATION");
            if (isCareerSection) {
                System.out.println(entry.getKey().getTitle() + ":\n");
                CareerSection careerSection = (CareerSection) entry.getValue();
                for (Career career : careerSection.getElement()) {
                    System.out.println(career.getTitle() + "\n" +
                            career.getStartDate().format(formatter) + " - " +
                            career.getEndDate().format(formatter) + "   " +
                            career.getPosition() + "\n                    " +
                            career.getDescription());
                }
                printLine();
            }
        }
    }

    private static void printLine() {
        System.out.println("------------------------------");
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
                    LocalDate.of(2013, 10, 1),
                    LocalDate.now());
            career_1.setDescription("Создание, организация и проведение Java онлайн проектов и стажировок");
            experience.add(career_1);

            Career career_2 = new Career("Wrike",
                    "Старший разработчик (backend)",
                    LocalDate.of(2014, 10, 1),
                    LocalDate.of(2016, 1, 1));
            career_2.setDescription("Проектирование и разработка онлайн платформы управления проектами Wrike " +
                    "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                    "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
            experience.add(career_2);

            Career career_3 = new Career("RIT Center",
                    "Java архитектор",
                    LocalDate.of(2012, 4, 1),
                    LocalDate.of(2014, 10, 1));
            career_3.setDescription("Организация процесса разработки системы ERP для разных окружений: " +
                    "релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                    "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. " +
                    "Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения " +
                    "(почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из " +
                    "браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, " +
                    "Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, " +
                    "Unix shell remote scripting via ssh tunnels, PL/Python.");
            experience.add(career_3);

            Career career_4 = new Career("Luxoft (Deutsche Bank)",
                    "Ведущий программист",
                    LocalDate.of(2010, 12, 1),
                    LocalDate.of(2012, 4, 1));
            career_4.setDescription("Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC," +
                    " SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация " +
                    "RIA-приложения для администрирования, мониторинга и анализа результатов в области " +
                    "алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
            experience.add(career_4);

            Career career_5 = new Career("Yota",
                    "Ведущий специалист",
                    LocalDate.of(2008, 6, 1),
                    LocalDate.of(2010, 12, 1));
            career_5.setDescription("Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                    "(GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). " +
                    "Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX " +
                    "клиента (Python/ Jython, Django, ExtJS).");
            experience.add(career_5);

            Career career_6 = new Career("Enkata",
                    "Разработчик ПО",
                    LocalDate.of(2007, 3, 1),
                    LocalDate.of(2008, 6, 1));
            career_6.setDescription("Реализация клиентской (Eclipse RCP) и серверной " +
                    "(JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
            experience.add(career_6);

            Career career_7 = new Career("Siemens AG",
                    "Разработчик ПО",
                    LocalDate.of(2005, 1, 1),
                    LocalDate.of(2007, 2, 1));
            career_7.setDescription("Разработка информационной модели, проектирование интерфейсов, реализация и " +
                    "отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
            experience.add(career_7);

            Career career_8 = new Career("Alcatel",
                    "Инженер по аппаратному и программному тестированию",
                    LocalDate.of(1997, 9, 1),
                    LocalDate.of(2005, 1, 1));
            career_8.setDescription("Тестирование, отладка, внедрение ПО цифровой телефонной " +
                    "станции Alcatel 1000 S12 (CHILL, ASM).");
            experience.add(career_8);

            return experience;
        }


        ArrayList<Career> getEducation() {
            ArrayList<Career> education = new ArrayList<>();
            Career education_1 = new Career("Coursera",
                    "\"Functional Programming Principles in Scala\" by Martin Odersky",
                    LocalDate.of(2013, 3, 1),
                    LocalDate.of(2013, 5, 1));
            education.add(education_1);

            Career education_2 = new Career("Luxoft",
                    "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                    LocalDate.of(2011, 3, 1),
                    LocalDate.of(2011, 4, 1));
            education.add(education_2);

            Career education_3 = new Career("Siemens AG",
                    "3 месяца обучения мобильным IN сетям (Берлин)",
                    LocalDate.of(2005, 1, 1),
                    LocalDate.of(2005, 4, 1));
            education.add(education_3);

            Career education_4 = new Career("Siemens AG",
                    "6 месяцев обучения цифровым телефонным сетям (Москва)",
                    LocalDate.of(1997, 9, 1),
                    LocalDate.of(1998, 3, 1));
            education.add(education_4);

            Career education_5 = new Career("Санкт-Петербургский национальный исследовательский " +
                    "университет информационных технологий, механики и оптики",
                    "Аспирантура (программист С, С++)",
                    LocalDate.of(1993, 9, 1),
                    LocalDate.of(1996, 7, 1));
            education.add(education_5);

            Career education_6 = new Career("Санкт-Петербургский национальный исследовательский " +
                    "университет информационных технологий, механики и оптики",
                    "Инженер (программист Fortran, C)",
                    LocalDate.of(1987, 9, 1),
                    LocalDate.of(1993, 7, 1));
            education.add(education_6);

            return education;
        }
    }
}
