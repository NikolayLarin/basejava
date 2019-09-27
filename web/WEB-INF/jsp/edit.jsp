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

        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${sectionType == objective}">
                    <b><c:out value="${sectionType.title}:"/></b><br/>
                    <c:if test="${resume.getSection(sectionType) != null}">
                        <c:set var="objectiveAbout"
                               value='<%=((AboutSection)resume.getSection(SectionType.valueOf("OBJECTIVE"))).getElement()%>'/>
                    </c:if>
                    <textarea rows="2" cols="120" name="${sectionType.name()}">${objectiveAbout}</textarea>
                    <br/><br/>
                </c:when>

                <c:when test="${sectionType == personal}">
                    <b><c:out value="${sectionType.title}:"/></b><br/>
                    <c:if test="${resume.getSection(sectionType) != null}">
                        <c:set var="personalAbout"
                               value='<%=((AboutSection)resume.getSection(SectionType.valueOf("PERSONAL"))).getElement()%>'/>
                    </c:if>
                    <textarea rows="2" cols="120" name="${sectionType.name()}">${personalAbout}</textarea>
                    <br/><br/>
                </c:when>

                <c:when test="${sectionType == achievement}">
                    <b><c:out value="${sectionType.title}:"/></b><br/>
                    <c:if test="${resume.getSection(sectionType) != null}">
                        <c:set var="achievementSkills"
                               value='<%=String.join("\n",
                               ((SkillsSection)resume.getSection(SectionType.valueOf("ACHIEVEMENT"))).getElement())%>'/>
                        <textarea rows="20" cols="120" name="${sectionType.name()}">${achievementSkills}</textarea>
                        <br/><br/>
                    </c:if>
                    Новые достижения (через Enter): <br/>
                    <textarea rows="6" cols="120" name="${sectionType.name()}"></textarea>
                    <br/><br/>
                </c:when>

                <c:when test="${sectionType == qualifications}">
                    <b><c:out value="${sectionType.title}:"/></b><br/>
                    <c:if test="${resume.getSection(sectionType) != null}">
                        <c:set var="qualificationsSkills"
                               value='<%=String.join("\n",
                               ((SkillsSection)resume.getSection(SectionType.valueOf("QUALIFICATIONS"))).getElement())%>'/>
                        <textarea rows="20" cols="120" name="${sectionType.name()}">${achievementSkills}</textarea>
                        <br/><br/>
                    </c:if>
                    Новые квалификации (через Enter): <br/>
                    <textarea rows="6" cols="120" name="${sectionType.name()}"></textarea>
                    <br/><br/>
                </c:when>
            </c:choose>
        </c:forEach>
        <br/>
        <button type="submit">Сохранить</button>
        <button type="reset">Отменить правки</button>
        <button onclick="window.history.back()">Выйти</button>
        <br/><br/>
        <a href="resume?uuid=${resume.uuid}&action=editDeprecated">Deprecated Edit realization</a>
        <br/>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>