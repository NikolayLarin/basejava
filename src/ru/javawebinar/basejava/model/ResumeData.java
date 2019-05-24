package ru.javawebinar.basejava.model;

public class ResumeData {

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

}