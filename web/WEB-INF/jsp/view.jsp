<%@ page import="ru.javawebinar.basejava.model.CareerSection" %>
<%@ page import="ru.javawebinar.basejava.model.SkillsSection" %>
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
    <p>
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
                    <%=sectionEntry.getValue()%><br/><br/>
                </c:when>
                <c:when test="${type.equals(achievement) || type.equals(qualifications)}">
                    <c:forEach var="skill" items="<%=((SkillsSection)sectionEntry.getValue()).getElement()%>">
                        • ${skill}<br/>
                    </c:forEach>
                    <br/>
                </c:when>
                <c:when test="${type.equals(experience) || type.equals(education)}">
                    <c:set var="careerList" value="<%=((CareerSection)sectionEntry.getValue()).getElement()%>"/>
                    <c:forEach var="career" items="${careerList}">
                        • ${career.title}:<br/>
                        <c:if test="${career.url != null}">
                            ${career.url}<br/>
                        </c:if>
                        <c:forEach var="posirion" items="${career.positions}">
                            ${posirion.startDate.toString()} - ${posirion.endDate.toString()}
                            ${posirion.position}<br/>
                            <c:if test="${posirion.description != null}">
                                ${posirion.description}<br/>
                            </c:if>
                        </c:forEach>
                        <br/>
                    </c:forEach>
                    <br/>
                </c:when>
            </c:choose>
            <br/><br/>
        </c:forEach>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>