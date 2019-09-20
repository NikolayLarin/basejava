package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.Config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage sqlStorage; // = Config.getINSTANCE().getSqlStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sqlStorage = Config.getINSTANCE().getSqlStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//        response.setHeader("Content-Type","text/html; charset=UTF-8");
        String name = request.getParameter("name");
        Writer writer = response.getWriter();
        writer.write(name == null ? "<h1>Hello Resumes!</h1>" : "Hello " + name + "!");
        String resumesTable = request.getParameter("resumes_table");
        if (resumesTable != null && resumesTable.equals("get")) {
            writer.write("<table>" +
                    "<table border=\"3\">" +
                    "<td><h3>" + "Resume's UUID" + "</h3></td>" +
                    "<td><h3>" + "Name" + "</h3></td>");
            List<Resume> resumes = sqlStorage.getAllSorted();
            for (Resume resume : resumes) {
                writer.write("<tr>" +
                        "<td><h4>" + resume.getUuid() + "</h4></td>" +
                        "<td><h4>" + resume.getFullName() + "</h4></td>" +
                        "</tr>");
            }
            writer.write("</table>");
        }
    }
}