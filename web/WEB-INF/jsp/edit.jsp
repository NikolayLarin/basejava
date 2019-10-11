<%@ page import="ru.javawebinar.basejava.model.AboutSection" %>
<%@ page import="ru.javawebinar.basejava.model.CareerSection" %>
<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.SkillsSection" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Resume ${resume.fullName}</title>
</head>
<body marginwidth="20" marginheight="20">
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>
                <h2>ФИО:</h2>
            </dt>
            <br/>
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
        <hr align="left" width="840"/>

        <h2>Секции:</h2>
        <c:set var="objective" value="OBJECTIVE"/>
        <c:set var="personal" value="PERSONAL"/>
        <c:set var="achievement" value="ACHIEVEMENT"/>
        <c:set var="qualifications" value="QUALIFICATIONS"/>
        <c:set var="experience" value="EXPERIENCE"/>
        <c:set var="education" value="EDUCATION"/>


        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.SectionType"/>
            <b><h3><c:out value="${sectionType.title}:"/></h3></b>
            <c:choose>
                <c:when test="${sectionType == objective || sectionType == personal}">
                    <c:set var="about"
                           value='<%=(resume.getSection(sectionType)) != null ?
                           ((AboutSection) resume.getSection(sectionType)).getElement() : "" %>'/>
                    <textarea title="Измените при необходимости" rows="2" cols="120"
                              name="${sectionType}">${about}</textarea>
                    <br/><br/>
                </c:when>
                <c:when test="${sectionType == achievement || sectionType == qualifications}">
                    <c:if test="${resume.getSection(sectionType) != null }">
                        <c:set var="skills"
                               value='<%=String.join("\n",
                               ((SkillsSection) resume.getSection(sectionType)).getElement())%>'/>
                        <textarea title="Новое добавьте через Enter" rows="12" cols="120"
                                  name="${sectionType}">${skills}</textarea>
                        <br/>
                    </c:if>
                    <p title="Новое добавьте через Enter">Добавьте новое</p>
                    <textarea title="Новое добавьте через Enter" rows="3" cols="120"
                              name="${sectionType}"></textarea>
                    <br/><br/>
                </c:when>

                <c:when test="${sectionType == experience || sectionType == education}">
                    <c:set var="prefix"
                           value='${(sectionType == experience) ? "experience" : "education"}'/>
                    <jsp:useBean id="prefix" type="java.lang.String"/>
                    <c:if test="${resume.getSection(sectionType) != null}">
                        <c:set var="careers"
                               value='<%=((CareerSection) resume.getSection(sectionType)).getElement()%>'/>
                        <c:forEach var="career" items="${careers}">
                            <c:forEach var="position" items="${career.positions}">
                                <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Career.Position"/>
                                <input title="Измените наименование"
                                       type="text" name="${sectionType.name()}" size=119
                                       value="${career.title}"><br/>
                                <input title="Измените веб-сайт"
                                       type="text" name='<%=prefix + "CareerUrl"%>' size=119
                                       value="${career.url}"><br/>
                                <input title="Измените дату начала"
                                       type="text" name='<%=prefix + "PositionStartDate"%>' size=6
                                       value="<%=DateUtil.format(position.getStartDate())%>"> –
                                <input title="Измените дату окончания"
                                       type="text" name='<%=prefix + "PositionEndDate"%>' size=6
                                       value="<%=DateUtil.format(position.getEndDate())%>"><br/>
                                <input title="Измените специализацию"
                                       type="text" name='<%=prefix + "Position"%>' size=119
                                       value="${position.position}"><br/>
                                <textarea title="Измените описание деятельности" rows="2" cols="119"
                                          name='<%=prefix + "PositionDescription"%>'>${position.description}</textarea><br/>
                                <p>Добавьте новое:</p>
                                <input hidden type="text" name="${sectionType.name()}" size=119
                                       value="${career.title}">
                                <input hidden type="text" name='<%=prefix + "CareerUrl"%>' size=119
                                       value="${career.url}">
                                <input title="Добавьте дату начала" type="text" placeholder="mm/yyyy"
                                       name='<%=prefix + "PositionStartDate"%>' size=6"> –
                                <input title="Добавьте дату окончания" type="text" placeholder="mm/yyyy"
                                       name='<%=prefix + "PositionEndDate"%>' size=6><br/>
                                <input title="Добавьте специализацию" type="text"
                                       name='<%=prefix + "Position"%>' size=119><br/>
                                <textarea title="Добавьте описание деятельности" rows="2" cols="119"
                                          name='<%=prefix + "PositionDescription"%>'></textarea><br/><br/>
                            </c:forEach>
                            <br/>
                        </c:forEach>
                    </c:if>
                    <p>Новая организация:</p>
                    <dl>
                        <dt>Наименование:</dt>
                        <dd><input type="text" name="${sectionType.name()}" size=60></dd>
                        <br/>
                        <dt>Веб-сайт:</dt>
                        <dd><input type="text" name='<%=prefix + "CareerUrl"%>' size=60></dd>
                        <br/>
                        <dt>Дата начала:</dt>
                        <dd><input type="text" name='<%=prefix + "PositionStartDate"%>'
                                   placeholder="mm/yyyy" size=8></dd>
                        <br/>
                        <dt>Дата окончания:</dt>
                        <dd><input type="text" name='<%=prefix + "PositionEndDate"%>'
                                   placeholder="mm/yyyy" size=8></dd>
                        <br/>
                        <dt>Специализация:</dt>
                        <dd><input type="text" name='<%=prefix + "Position"%>' size=60></dd>
                        <br/>
                    </dl>
                    <p>Описание деятельности:</p>
                    <textarea rows="3" cols="119" name='<%=prefix + "PositionDescription"%>'></textarea><br/>
                    <br/>
                </c:when>
            </c:choose>
            <button type="submit">Сохранить</button>
            <button type="reset">Отменить правки</button>
            <button onclick="window.history.back()">Выйти</button>
            <hr align="left" width="840"/><br/>
        </c:forEach>
        <br/>
        <br/>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>