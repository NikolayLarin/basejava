<%@ page import="ru.javawebinar.basejava.model.AboutSection" %>
<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
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
        <h2>ФИО:</h2>
        <br>
        <input readonly type="text" name="fullName" size=89 value="${resume.fullName}">
        <br/><br/>

        <c:forEach var="type" items="<%=ContactType.values()%>">
            <input type="hidden" name="${type.name()}" size=60 value="${resume.getContact(type)}">
        </c:forEach>

        <c:set var="objective" value="OBJECTIVE"/>
        <c:set var="personal" value="PERSONAL"/>
        <c:set var="achievement" value="ACHIEVEMENT"/>
        <c:set var="qualifications" value="QUALIFICATIONS"/>
        <c:set var="experience" value="EXPERIENCE"/>
        <c:set var="education" value="EDUCATION"/>

        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${sectionType == objective}">
                    <c:if test="${resume.getSection(sectionType) != null}">
                        <c:set var="objectiveAbout"
                               value='<%=((AboutSection)resume.getSection(SectionType.valueOf("OBJECTIVE"))).getElement()%>'/>
                    </c:if>
                    <textarea hidden rows="2" cols="120" name="${sectionType.name()}">${objectiveAbout}</textarea>
                </c:when>

                <c:when test="${sectionType == personal}">
                    <c:out value="${sectionType.title}:"/><br/>
                    <c:if test="${resume.getSection(sectionType) != null}">
                        <c:set var="personalAbout"
                               value='<%=((AboutSection)resume.getSection(SectionType.valueOf("PERSONAL"))).getElement()%>'/>
                    </c:if>
                    <textarea rows="2" cols="120" name="${sectionType.name()}">${personalAbout}</textarea>
                </c:when>

                <c:when test="${sectionType == achievement}">
                    <c:if test="${resume.getSection(sectionType) != null}">
                        <c:set var="achievementSkills"
                               value='<%=((SkillsSection)resume.getSection(SectionType.valueOf("ACHIEVEMENT"))).getElement()%>'/>
                        <c:forEach var="skill" items="${achievementSkills}">
                            <textarea hidden rows="2" cols="120" name="${sectionType.name()}">${skill}</textarea>
                        </c:forEach>
                    </c:if>
                </c:when>

                <c:when test="${sectionType == qualifications}">
                    <c:if test="${resume.getSection(sectionType) != null}">
                        <c:set var="qualificationsSkills"
                               value='<%=((SkillsSection)resume.getSection(SectionType.valueOf("QUALIFICATIONS"))).getElement()%>'/>
                        <c:forEach var="skill" items="${qualificationsSkills}">
                            <textarea hidden rows="2" cols="120" name="${sectionType.name()}">${skill}</textarea>
                        </c:forEach>
                    </c:if>
                </c:when>
            </c:choose>
        </c:forEach>

        <br/>
        <button type="submit">Сохранить</button>
        <button type="reset">Отменить правки</button>
        <button onclick="window.history.back()">Выйти</button>
        <br/><br/>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>