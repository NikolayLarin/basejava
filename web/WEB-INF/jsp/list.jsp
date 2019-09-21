<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>All resumes list</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table border="3" cellpadding="8" cellspacing="0">
        <tr>
            <th>Name</th>
            <th>E-mail</th>
        </tr>
        <%
            for (Resume resume : (List<Resume>) request.getAttribute("resumes")) {
        %>
        <tr>
            <td><a href="resume?uuid=<%=resume.getUuid()%>"><%=resume.getFullName()%>
            </a>
            </td>
            <td><%=resume.getContact(ContactType.EMAIL)%>
            </td>
            <%
                }
            %>
        </tr>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>