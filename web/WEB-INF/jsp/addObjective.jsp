<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.SkillsSection" %>
<%@ page import="ru.javawebinar.basejava.model.AboutSection" %>
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
            <dd><input type="text" name="fullName" size=89 value="${resume.fullName}"></dd>
        </dl>
        <c:set var="objective" value="OBJECTIVE"/>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:if test="${sectionType.name().equals(objective)}">
                <c:if test="${resume.getSection(sectionType) != null}">
                    <c:set var="about"
                           value='<%=((AboutSection)resume.getSection(SectionType.valueOf("OBJECTIVE"))).getElement()%>'/>
                    <b>${sectionType.title}:</b><br/>
                        <textarea rows="2" cols="120" name="${sectionType.name()}">${about}</textarea><br/>
                </c:if>
                <textarea rows="2" cols="120" name="${sectionType.name()}"></textarea><br/>
            </c:if>
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