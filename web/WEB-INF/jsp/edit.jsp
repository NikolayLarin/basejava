<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SkillsSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Resume ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <h2>
                <dt>ФИО:</dt>
            </h2>
            <dd><input type="text" name="fullName" required size=89 value="${resume.fullName}"></dd>
        </dl>

        <h2>Контакты:</h2>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}:</dt>
                <dd><input type="text" name="${type.name()}" size=60 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button type="reset">Отменить правки</button>
        <button onclick="window.history.back()">Выйти</button>

        <h2>Секции:</h2>
        <c:set var="objective" value="OBJECTIVE"/>
        <c:set var="personal" value="PERSONAL"/>
        <c:set var="achievement" value="ACHIEVEMENT"/>
        <c:set var="qualifications" value="QUALIFICATIONS"/>
        <c:set var="experience" value="EXPERIENCE"/>
        <c:set var="education" value="EDUCATION"/>
        <c:forEach var="sectionEntry" items="<%=resume.getSections().entrySet()%>">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType,
                                 ru.javawebinar.basejava.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key.name()}"/>
            <b>${sectionEntry.key.title}:
            </b><br/>
            <c:choose>
                <c:when test="${type.equals(objective) || type.equals(personal)}">
                    <textarea rows="2" cols="120" name="${type}">${sectionEntry.value}</textarea>
                    <br/><br/>
                </c:when>
                <c:when test="${type.equals(achievement) || type.equals(qualifications)}">
                    <c:forEach var="skill" items="<%=((SkillsSection)sectionEntry.getValue()).getElement()%>">
                        <textarea rows="2" cols="120" name="${type}">${skill}</textarea>
                        <br/><br/>
                    </c:forEach>
                </c:when>
            </c:choose>
            <br/>
        </c:forEach>
        <a href="resume?uuid=${resume.uuid}&action=addObjective">Изменить позицию</a>
        <a href="resume?uuid=${resume.uuid}&action=addPersonal">Изменить личные качества</a>
        <a href="resume?uuid=${resume.uuid}&action=addAchievement">Добавить достижения</a>
        <a href="resume?uuid=${resume.uuid}&action=addQualification">Добавить квалификацию</a>
        <br/><br/>
        <button type="submit">Сохранить</button>
        <button type="reset">Отменить правки</button>
        <button onclick="window.history.back()">Выйти</button>
        <br/>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>