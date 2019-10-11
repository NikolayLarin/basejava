<%@ page import="ru.javawebinar.basejava.model.AboutSection" %>
<%@ page import="ru.javawebinar.basejava.model.CareerSection" %>
<%@ page import="ru.javawebinar.basejava.model.SkillsSection" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Resume ${resume.fullName}</title>
</head>
<body marginwidth="20" marginheight="20">
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}
        <a href="resume?uuid=${resume.uuid}&action=edit">
            <img src="img/resume-edit.png" width="35" height="35" title="edit resume" align="absbottom"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <hr>
    <br/>

    <c:set var="objective" value="OBJECTIVE"/>
    <c:set var="personal" value="PERSONAL"/>
    <c:set var="achievement" value="ACHIEVEMENT"/>
    <c:set var="qualifications" value="QUALIFICATIONS"/>
    <c:set var="experience" value="EXPERIENCE"/>
    <c:set var="education" value="EDUCATION"/>

    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType,
                                 ru.javawebinar.basejava.model.AbstractSection>"/>
        <c:set var="type" value="${sectionEntry.key}"/>
        <c:set var="section" value="${sectionEntry.value}"/>
        <jsp:useBean id="section" type="ru.javawebinar.basejava.model.AbstractSection"/>
        <c:choose>
            <c:when test="${type == objective || type == personal}">
                <c:if test="<%=!((AboutSection) section).getElement().trim().isEmpty()%>">
                    <h3><b>${sectionEntry.key.title}:</b></h3>
                    <p>${sectionEntry.value}</p>
                </c:if>
                <br/>
            </c:when>
            <c:when test="${type == achievement || type == qualifications}">
                <c:if test="<%=((SkillsSection) section).getElement().size() != 0%>">
                    <h3><b>${sectionEntry.key.title}:</b></h3>
                    <ul>
                        <c:forEach var="skill" items="<%=((SkillsSection) section).getElement()%>">
                            <li>${skill}</li>
                            <br/>
                        </c:forEach>
                    </ul>
                </c:if>
            </c:when>
            <c:when test="${type == experience || type == education}">
                <c:if test="<%=((CareerSection) section).getElement().size() != 0%>">
                    <h3><b>${sectionEntry.key.title}:</b></h3>
                    <c:set var="careerList" value="<%=((CareerSection) section).getElement()%>"/>
                    <ul>
                        <c:forEach var="career" items="${careerList}">
                            <li><em><b>${career.title}:</b></em></li>
                            <c:if test="${career.url != null}">
                                <a href="${career.url}" target="_blank">${career.url}</a><br/>
                            </c:if>
                            <c:forEach var="position" items="${career.positions}">
                                <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Career.Position"/>
                                <c:out value="<%=DateUtil.format(position.getStartDate())%>"/> –
                                <c:out value="<%=DateUtil.format(position.getEndDate())%>"/>
                                <em><b>${position.position}</b></em><br/>
                                <c:if test="${position.description != null}">
                                    ${position.description}<br/>
                                </c:if>
                            </c:forEach>
                            <br/>
                        </c:forEach>
                    </ul>
                    <br/>
                </c:if>
            </c:when>
        </c:choose>
    </c:forEach>
    <button onclick="window.history.back()">OK</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>