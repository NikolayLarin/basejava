package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.AboutSection;
import ru.javawebinar.basejava.model.AbstractSection;
import ru.javawebinar.basejava.model.Career;
import ru.javawebinar.basejava.model.CareerSection;
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
        Resume testResume = createResume("uuid", "Григорий Кислин");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/YYYY");

        for (EnumMap.Entry<ContactType, String> entry : testResume.getContacts().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
        }
        printLine();

        for (EnumMap.Entry<SectionType, AbstractSection> entry : testResume.getSections().entrySet()) {
            boolean isAboutSection = entry.getKey().name().equals("OBJECTIVE") || entry.getKey().name().equals("PERSONAL");
            if (isAboutSection) {
                System.out.println(entry.getKey().getTitle() + ": \n" + dot() + entry.getValue());
                printLine();
            }
        }

        for (EnumMap.Entry<SectionType, AbstractSection> entry : testResume.getSections().entrySet()) {
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

        for (EnumMap.Entry<SectionType, AbstractSection> entry : testResume.getSections().entrySet()) {
            boolean isCareerSection = entry.getKey().name().equals("EXPERIENCE") || entry.getKey().name().equals("EDUCATION");
            if (isCareerSection) {
                System.out.println(entry.getKey().getTitle() + ":");
                CareerSection careerSection = (CareerSection) entry.getValue();
                for (Career list : careerSection.getElement()) {
                    System.out.println("\n" + list.getTitle());
                    String url = list.getUrl();
                    if (url != null) {
                        System.out.println(url);
                    }
                    for (Career.Position position : list.getPositions()) {

                        System.out.println(position.getStartDate().format(formatter) + " - " +
                                position.getEndDate().format(formatter) + "   " +
                                position.getPosition() + "                    ");
                        String description = position.getDescription();
                        if (description != null) {
                            System.out.print(description + "\n");
                        }
                    }
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

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        ResumeTestData.ResumeData data = new ResumeTestData().new ResumeData();

        resume.setContact(ContactType.PHONE, data.phone);
        resume.setContact(ContactType.SKYPE, data.skype);
        resume.setContact(ContactType.EMAIL, data.email);
        resume.setContact(ContactType.LINKEDIN, data.linkedin);
        resume.setContact(ContactType.GITHUB, data.gitHub);
        resume.setContact(ContactType.STACKOVERFLOW, data.stackOverflow);
        resume.setContact(ContactType.SITE, data.site);

        resume.setSection(SectionType.OBJECTIVE, new AboutSection(data.objective));
        resume.setSection(SectionType.PERSONAL, new AboutSection(data.personal));
        resume.setSection(SectionType.ACHIEVEMENT, new SkillsSection(data.getAchievements()));
        resume.setSection(SectionType.QUALIFICATIONS, new SkillsSection(data.getQualifications()));
//        resume.setSection(SectionType.EXPERIENCE, new CareerSection(data.getExperience()));
//        resume.setSection(SectionType.EDUCATION, new CareerSection(data.getEducation()));
        return resume;
    }

    private class ResumeData {
        private String phone = "+7(921) 855-0482";
        private String skype = "grigory.kislin";
        private String email = "gkislin@yandex.ru";
        private String linkedin = "https://www.linkedin.com/in/gkislin";
        private String gitHub = "https://github.com/gkislin";
        private String stackOverflow = "https://stackoverflow.com/users/548473";
        private String site = "http://gkislin.ru/";

        private String objective = "Ведущий стажировок " +
                "и корпоративного обучения по Java Web и Enterprise технологиям";
        private String personal = "Аналитический склад ума, сильная логика, креативность, " +
                "инициативность. Пурист кода и архитектуры.";

        private ArrayList<String> getAchievements() {
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

        private ArrayList<String> getQualifications() {
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

        private List<Career> getExperience() {
            List<Career> experience = new ArrayList<>();

            String title_1 = "Java Online Projects";
            Career experience_1 = new Career(title_1, "http://javaops.ru/");
            Career.Position position_1 = new Career.Position("Автор проекта", 2013, 10);
            position_1.setDescription("Создание, организация и проведение Java онлайн проектов и стажировок");
            experience_1.addPosition(title_1, position_1);
            experience.add(experience_1);

            String title_2 = "Wrike";
            Career experience_2 = new Career(title_2, "https://www.wrike.com/");
            Career.Position position_2 = new Career.Position("Старший разработчик (backend)",
                    2014, 10, 2016, 1);
            position_2.setDescription("Проектирование и разработка онлайн платформы управления проектами Wrike " +
                    "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                    "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
            experience_2.addPosition(title_2, position_2);
            experience.add(experience_2);

            String title_3 = "RIT Center";
            Career experience_3 = new Career(title_3);
            Career.Position position_3 = new Career.Position("Java архитектор",
                    2012, 4, 2014, 10);
            position_3.setDescription("Организация процесса разработки системы ERP для разных окружений: " +
                    "релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                    "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. " +
                    "Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения " +
                    "(почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из " +
                    "браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, " +
                    "Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, " +
                    "Unix shell remote scripting via ssh tunnels, PL/Python.");
            experience_3.addPosition(title_3, position_3);
            experience.add(experience_3);

            String title_4 = "Luxoft (Deutsche Bank)";
            Career experience_4 = new Career(title_4, "http://www.luxoft.ru/");
            Career.Position position_4 = new Career.Position("Ведущий программист",
                    2010, 12, 2012, 4);
            position_4.setDescription("Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC," +
                    "SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация " +
                    "RIA-приложения для администрирования, мониторинга и анализа результатов в области " +
                    "алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
            experience_4.addPosition(title_4, position_4);
            experience.add(experience_4);

            String title_5 = "Yota";
            Career experience_5 = new Career(title_5, "https://www.yota.ru/");
            Career.Position position_5 = new Career.Position("Ведущий специалист",
                    2008, 6, 2010, 12);
            position_5.setDescription("Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                    "(GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). " +
                    "Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX " +
                    "клиента (Python/ Jython, Django, ExtJS).");
            experience_5.addPosition(title_5, position_5);
            experience.add(experience_5);

            String title_6 = "Enkata";
            Career experience_6 = new Career(title_6, "http://enkata.com/");
            Career.Position position_6 = new Career.Position("Разработчик ПО",
                    2007, 3, 2008, 6);
            position_6.setDescription("Реализация клиентской (Eclipse RCP) и серверной " +
                    "(JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
            experience_6.addPosition(title_6, position_6);
            experience.add(experience_6);

            String title_7 = "Siemens AG";
            Career experience_7 = new Career(title_7, "https://www.siemens.com/ru/ru/home.html");
            Career.Position position_7 = new Career.Position("Разработчик ПО",
                    2005, 1, 2007, 2);
            position_7.setDescription("Разработка информационной модели, проектирование интерфейсов, реализация и " +
                    "отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
            experience_7.addPosition(title_7, position_7);
            experience.add(experience_7);

            String title_8 = "Alcatel";
            Career experience_8 = new Career(title_8, "http://www.alcatel.ru/");
            Career.Position position_8 = new Career.Position("Инженер по аппаратному и программному тестированию",
                    1997, 9, 2005, 1);
            position_8.setDescription("Тестирование, отладка, внедрение ПО цифровой телефонной " +
                    "станции Alcatel 1000 S12 (CHILL, ASM).");
            experience_8.addPosition(title_8, position_8);
            experience.add(experience_8);

            return experience;
        }

        private List<Career> getEducation() {
            List<Career> education = new ArrayList<>();

            String title_1 = "Coursera";
            Career education_1 = new Career(title_1, "https://www.coursera.org/course/progfun");
            Career.Position position_1 = new Career.Position("\"Functional Programming " +
                    "Principles in Scala\" by Martin Odersky",
                    LocalDate.of(2013, 3, 1),
                    LocalDate.of(2013, 5, 1));
            education_1.addPosition(title_1, position_1);
            education.add(education_1);

            String title_2 = "Luxoft";
            Career education_2 = new Career(title_2,
                    "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366");
            Career.Position position_2 = new Career.Position("Курс \"Объектно-ориентированный анализ ИС. " +
                    "Концептуальное моделирование на UML.\"",
                    2011, 3, 2011, 4);
            education_2.addPosition(title_2, position_2);
            education.add(education_2);

            String title_3 = "Siemens AG";
            Career education_3 = new Career(title_3, "http://www.siemens.ru/");
            Career.Position position_3 = new Career.Position("3 месяца обучения мобильным IN сетям (Берлин)",
                    2005, 1, 2005, 4);
            education_3.addPosition(title_3, position_3);
            education.add(education_3);

            String title_4 = "Alcatel";
            Career education_4 = new Career(title_4, "http://www.alcatel.ru/");
            Career.Position position_4 = new Career.Position("6 месяцев обучения цифровым телефонным сетям (Москва)",
                    1997, 9, 1998, 3);
            education_4.addPosition(title_4, position_4);
            education.add(education_4);

            String title_5 = "Санкт-Петербургский национальный исследовательский университет " +
                    "информационных технологий, механики и оптики";
            Career education_5 = new Career(title_5, "http://www.ifmo.ru/");
            Career.Position position_5 = new Career.Position("Аспирантура (программист С, С++)",
                    1993, 9, 1996, 7);
            education_5.addPosition(title_5, position_5);
            Career.Position position_6 = new Career.Position("Инженер (программист Fortran, C)",
                    1987, 9, 1993, 7);
            education_5.addPosition(title_5, position_6);
            education.add(education_5);

            return education;
        }
    }
}