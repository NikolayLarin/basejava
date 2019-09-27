<%@ page import="ru.javawebinar.basejava.model.CareerSection" %>
<%@ page import="ru.javawebinar.basejava.model.SkillsSection" %>
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
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p><br/>

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
        <b><%=sectionEntry.getKey().getTitle()%>:</b><br/><br/>
        <c:set var="type" value="${sectionEntry.key.name()}"/>
        <c:choose>
            <c:when test="${type.equals(objective) || type.equals(personal)}">
                <%=sectionEntry.getValue()%><br/>
                <br/>
            </c:when>

            <c:when test="${type.equals(achievement) || type.equals(qualifications)}">
                <ul>
                    <c:forEach var="skill" items="<%=((SkillsSection)sectionEntry.getValue()).getElement()%>">
                        <li>${skill}</li>
                        <br/>
                    </c:forEach>
                </ul>
            </c:when>

            <c:when test="${type.equals(experience) || type.equals(education)}">
                <c:set var="formatter" value='<%=DateTimeFormatter.ofPattern("MM/YYYY")%>'/>
                <c:set var="careerList" value="<%=((CareerSection)sectionEntry.getValue()).getElement()%>"/>
                <ul>
                    <c:forEach var="career" items="${careerList}">
                        <li><em><b>${career.title}:</b></em></li>
                        <c:if test="${career.url != null}">
                            ${career.url}<br/>
                        </c:if>
                        <c:forEach var="position" items="${career.positions}">
                            ${position.startDate.format(formatter)} – ${position.endDate.format(formatter)}
                            <em><b>${position.position}</b></em><br/>
                            <c:if test="${position.description != null}">
                                ${position.description}<br/>
                            </c:if>
                        </c:forEach>
                        <br/>
                    </c:forEach>
                </ul>
                <br/>
            </c:when>

        </c:choose>
        <br/>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>