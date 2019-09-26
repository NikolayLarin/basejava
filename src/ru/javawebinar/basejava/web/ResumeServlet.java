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
                resume.removeContact(contactType);
            }
        }
        for (SectionType sectionType : SectionType.values()) {
            String sectionTypeName = sectionType.name();
            String value = request.getParameter(sectionTypeName);
            if (value != null) {
                switch (sectionTypeName) {
                    case ("OBJECTIVE"):
                    case ("PERSONAL"):
                        if (value.trim().isEmpty()) {
                            resume.removeSection(sectionType);
                        } else {
                            resume.setSection(sectionType, new AboutSection(value));
                        }
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
                        break;
                }
            } else {
                resume.removeSection(sectionType);
            }
        }
        if (resume.getFullName().trim().length() == 0) {
            sqlStorage.delete(uuid);
        } else {
            sqlStorage.update(resume);
        }
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
                forward(request, response, uuid, "WEB-INF/jsp/view.jsp");
                break;
            case "edit":
                forward(request, response, uuid, "WEB-INF/jsp/edit.jsp");
                break;
            case "add":
                resume = new Resume("");
                sqlStorage.save(resume);
                forward(request, response, resume.getUuid(),"WEB-INF/jsp/edit.jsp");
                break;
            case "addObjective":
                forward(request, response, uuid, "WEB-INF/jsp/addObjective.jsp");
                break;
            case "addPersonal":
                forward(request, response, uuid, "WEB-INF/jsp/addPersonal.jsp");
                break;
            case "addAchievement":
                forward(request, response, uuid, "WEB-INF/jsp/addAchievement.jsp");
                break;
            case "addQualification":
                forward(request, response, uuid, "WEB-INF/jsp/addQualification.jsp");
                break;
            default:
                throw new IllegalStateException("Action " + action + "is illegal");
        }
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String uuid, String jsp) throws ServletException, IOException {
        Resume resume = sqlStorage.get(uuid);
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(jsp).forward(request, response);
    }
}