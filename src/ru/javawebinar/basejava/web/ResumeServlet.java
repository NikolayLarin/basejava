package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.util.Config;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//        response.setHeader("Content-Type","text/html; charset=UTF-8");
        String name = request.getParameter("name");
        response.getWriter().write(name == null ? "<h1>Hello Resumes!</h1>" : "Hello " + name + "!");
        String resumesTable = request.getParameter("resumes_table");
        if (resumesTable != null && resumesTable.equals("get")) {
            response.getWriter().write("<table>");
            response.getWriter().write("<table border=\"3\">");
            response.getWriter().write("<td><h3>" + "Resume's UUID" + "</h3></td>");
            response.getWriter().write("<td><h3>" + "Name" + "</h3></td>");
            for (Resume resume : Config.getINSTANCE().getSqlStorage().getAllSorted()) {
                response.getWriter().write("<tr>");
                response.getWriter().write("<td><h4>" + resume.getUuid() + "</h4></td>");
                response.getWriter().write("<td><h4>" + resume.getFullName() + "</h4></td>");
                response.getWriter().write("</tr>");
            }
            response.getWriter().write("</table>");
        }
    }
}