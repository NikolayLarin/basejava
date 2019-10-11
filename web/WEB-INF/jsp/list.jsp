<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>All resumes list</title>
</head>
<body marginwidth="20" marginheight="20">
<jsp:include page="fragments/header.jsp"/>
<section>
    <a href="resume?uuid=${null}&action=add">
        <img src="img/resume-add.png" width="35" height="35" title="add resume"></a><br/><br/>
    <table border="3" cellpadding="8" cellspacing="0">
        <tr>
            <th>Name</th>
            <th>E-mail, phone</th>
            <th>Action</th>
            <th>Action</th>
        </tr>
        <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%><br/>
                    <%=ContactType.PHONE.toHtml(resume.getContact(ContactType.PHONE))%>
                </td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit">
                    <img src="img/resume-edit.png" width="35" height="35" title="edit resume"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete">
                <img src="img/resume-delete.png" width="35" height="35" title="delete resume"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>