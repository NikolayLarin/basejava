package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.AboutSection;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.model.SkillsSection;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.Config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage sqlStorage; // = Config.getINSTANCE().getSqlStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sqlStorage = Config.getINSTANCE().getSqlStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        final Resume resume = sqlStorage.get(uuid);
        resume.setFullName(fullName);
        for (ContactType contactType : ContactType.values()) {
            String value = request.getParameter(contactType.name());
            if (value != null && value.trim().length() != 0) {
                resume.setContact(contactType, value);
            } else {
                resume.getContacts().remove(contactType);
            }
        }
        for (SectionType sectionType : SectionType.values()) {
            String sectionTypeName = sectionType.name();
            String value = request.getParameter(sectionTypeName);
            if (value != null && value.trim().length() != 0) {
                switch (sectionTypeName) {
                    case ("OBJECTIVE"):
                    case ("PERSONAL"):
                        resume.setSection(sectionType, new AboutSection(value));
                        break;
                    case ("ACHIEVEMENT"):
                    case ("QUALIFICATIONS"):
                        List<String> skills = new ArrayList<>();
                        String[] values = request.getParameterValues(sectionTypeName);
                        for (String el : values) {
                            if (el != null && el.trim().length() != 0) {
                                skills.add(el);
                            }
                        }
                        resume.setSection(sectionType, new SkillsSection(skills));
                        break;
                    case ("EXPERIENCE"):
                    case ("EDUCATION"):
                }
            } else {
                resume.getSections().remove(sectionType);
            }
        }
        sqlStorage.update(resume);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", sqlStorage.getAllSorted());
            request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                sqlStorage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = sqlStorage.get(uuid);
                break;
            default:
                throw new IllegalStateException("Action " + action + "is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                action.equals("view") ? "WEB-INF/jsp/view.jsp" : "WEB-INF/jsp/edit.jsp"
        ).forward(request, response);
    }
}