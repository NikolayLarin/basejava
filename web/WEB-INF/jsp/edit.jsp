<%@ page import="ru.javawebinar.basejava.model.AboutSection" %>
<%@ page import="ru.javawebinar.basejava.model.CareerSection" %>
<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.SkillsSection" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
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
        <c:set var="formatter" value='<%=DateTimeFormatter.ofPattern("MM/YYYY")%>'/>


        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:if test="${sectionType == objective}">
                <b><h3><c:out value="${sectionType.title}:"/></h3></b>
                <c:if test="${resume.getSection(sectionType) != null}">
                    <c:set var="objectiveAbout"
                           value='<%=((AboutSection)resume.getSection(SectionType.valueOf("OBJECTIVE"))).getElement()%>'/>
                </c:if>
                <textarea rows="2" cols="120" name="${sectionType.name()}">${objectiveAbout}</textarea>
                <br/><br/>
            </c:if>

            <c:if test="${sectionType == personal}">
                <b><h3><c:out value="${sectionType.title}:"/></h3></b>
                <c:if test="${resume.getSection(sectionType) != null}">
                    <c:set var="personalAbout"
                           value='<%=((AboutSection)resume.getSection(SectionType.valueOf("PERSONAL"))).getElement()%>'/>
                </c:if>
                <textarea rows="2" cols="120" name="${sectionType.name()}">${personalAbout}</textarea>
                <br/><br/>
            </c:if>

            <c:if test="${sectionType == achievement}">
                <b><h3><c:out value="${sectionType.title}:"/></h3></b>
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
            </c:if>

            <c:if test="${sectionType == qualifications}">
                <b><h3><c:out value="${sectionType.title}:"/></h3></b>
                <c:if test="${resume.getSection(sectionType) != null}">
                    <c:set var="qualificationsSkills"
                           value='<%=String.join("\n",
                               ((SkillsSection)resume.getSection(SectionType.valueOf("QUALIFICATIONS"))).getElement())%>'/>
                    <textarea rows="20" cols="120" name="${sectionType.name()}">${qualificationsSkills}</textarea>
                    <br/><br/>
                </c:if>
                Новые квалификации (через Enter): <br/>
                <textarea rows="6" cols="120" name="${sectionType.name()}"></textarea>
                <br/><br/>
            </c:if>

            <c:if test="${sectionType == experience}">
                <b><h3><c:out value="${sectionType.title}:"/></h3></b>
                <c:if test="${resume.getSection(sectionType) != null}">
                    <c:set var="experienceList"
                           value='<%=((CareerSection)resume.getSection(SectionType.valueOf("EXPERIENCE"))).getElement()%>'/>
                    <c:forEach var="experienceCareer" items="${experienceList}">
                        <c:forEach var="experiencePosition" items="${experienceCareer.positions}">
                            <input type="text" name="${sectionType.name()}" size=120
                                   value="${experienceCareer.title}"><br/>
                            <input type="text" name="experienceCareerUrl" size=120
                                   value="${experienceCareer.url}"><br/>
                            <input type="text" name="experiencePositionStartDate" size=8
                                   value="${experiencePosition.startDate.format(formatter)}"> –
                            <input type="text" name="experiencePositionEndDate" size=108
                                   value="${experiencePosition.endDate.format(formatter)}"><br/>
                            <input type="text" name="experiencePosition" size=120
                                   value="${experiencePosition.position}"><br/>
                            <textarea rows="2" cols="119"
                                      name="experiencePositionDescription">${experiencePosition.description}</textarea><br/>
                            Новая должность:<br>
                            <input hidden type="text" name="${sectionType.name()}" size=120
                                   value="${experienceCareer.title}">
                            <input hidden type="text" name="experienceCareerUrl" size=120
                                   value="${experienceCareer.url}">
                            <input title="Добавьте дату начала" type="text" name="experiencePositionStartDate"
                                   size=8"> –
                            <input title="Добавьте дату окончания" type="text" name="experiencePositionEndDate"
                                   size=108><br/>
                            <input title="Добавьте Должность" type="text" name="experiencePosition"
                                   size=120><br/>
                            <textarea title="Добавьте описание деятельности" rows="2" cols="119"
                                      name="experiencePositionDescription"></textarea><br/>
                        </c:forEach>
                        <br/>
                    </c:forEach>
                </c:if>
                Новый опыт работы: <br/>
                <dl>
                    <dt>Компнаия:</dt>
                    <dd><input type="text" name="${sectionType.name()}" size=60></dd>
                    <br/>
                    <dt>Сайт компании:</dt>
                    <dd><input type="text" name="experienceCareerUrl" size=60></dd>
                    <br/>
                    <dt>Дата начала:</dt>
                    <dd><input type="text" name="experiencePositionStartDate" size=60></dd>
                    <br/>
                    <dt>Дата окончания:</dt>
                    <dd><input type="text" name="experiencePositionEndDate" size=60></dd>
                    <br/>
                    <dt>Должность:</dt>
                    <dd><input type="text" name="experiencePosition" size=60></dd>
                    <br/>
                </dl>
                Описание деятельности:<br/>
                <textarea rows="3" cols="119" name="experiencePositionDescription"></textarea><br/>
                <br/>
            </c:if>

            <c:if test="${sectionType == education}">
                <b><h3><c:out value="${sectionType.title}:"/></h3></b>
                <c:if test="${resume.getSection(sectionType) != null}">
                    <c:set var="educationList"
                           value='<%=((CareerSection)resume.getSection(SectionType.valueOf("EDUCATION"))).getElement()%>'/>
                    <c:forEach var="educationCareer" items="${educationList}">
                        <c:forEach var="educationPosition" items="${educationCareer.positions}">
                            <input type="text" name="${sectionType.name()}" size=120
                                   value="${educationCareer.title}"><br/>
                            <input type="text" name="educationCareerUrl" size=120
                                   value="${educationCareer.url}"><br/>
                            <input type="text" name="educationPositionStartDate" size=8
                                   value="${educationPosition.startDate.format(formatter)}"> –
                            <input type="text" name="educationPositionEndDate" size=108
                                   value="${educationPosition.endDate.format(formatter)}"><br/>
                            <input type="text" name="educationPosition" size=120
                                   value="${educationPosition.position}"><br/>
                            <textarea rows="2" cols="119"
                                      name="educationPositionDescription">${educationPosition.description}</textarea><br/>
                            Новая позиция:<br>
                            <input hidden type="text" name="${sectionType.name()}" size=120
                                   value="${educationCareer.title}">
                            <input hidden type="text" name="educationCareerUrl" size=120
                                   value="${educationCareer.url}">
                            <input title="Добавьте дату начала" type="text" name="educationPositionStartDate"
                                   size=8"> –
                            <input title="Добавьте дату окончания" type="text" name="educationPositionEndDate"
                                   size=108><br/>
                            <input title="Добавьте специализацию" type="text" name="educationPosition"
                                   size=120><br/>
                            <textarea title="Добавьте описание" rows="2" cols="119"
                                      name="educationPositionDescription"></textarea><br/>
                        </c:forEach>
                        <br/>
                    </c:forEach>
                </c:if>
                Новое учереждение: <br/>
                <dl>
                    <dt>Наименование:</dt>
                    <dd><input type="text" name="${sectionType.name()}" size=60></dd>
                    <br/>
                    <dt>Сайт учереждения:</dt>
                    <dd><input type="text" name="educationCareerUrl" size=60></dd>
                    <br/>
                    <dt>Дата начала:</dt>
                    <dd><input type="text" name="educationPositionStartDate" size=60></dd>
                    <br/>
                    <dt>Дата окончания:</dt>
                    <dd><input type="text" name="educationPositionEndDate" size=60></dd>
                    <br/>
                    <dt>Специализация:</dt>
                    <dd><input type="text" name="educationPosition" size=60></dd>
                    <br/>
                </dl>
                Описание:<br/>
                <textarea rows="3" cols="119" name="educationPositionDescription"></textarea><br/>
                <br/>
            </c:if>
            <button type="submit">Сохранить</button>
            <button type="reset">Отменить правки</button>
            <button onclick="window.history.back()">Выйти</button>
            <br/>
        </c:forEach>
        <br/>
        <br/>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>