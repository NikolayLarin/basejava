package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.AboutSection;
import ru.javawebinar.basejava.model.Career;
import ru.javawebinar.basejava.model.CareerSection;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Link;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.model.SkillsSection;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.Config;
import ru.javawebinar.basejava.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            String[] values = request.getParameterValues(sectionTypeName);
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
                        for (String joinedString : values) {
                            String[] el = joinedString.split("\n");
                            for (String s : el) {
                                if (s != null && !s.trim().isEmpty()) {
                                    skills.add(s);
                                }
                            }
                        }
                        resume.setSection(sectionType, new SkillsSection(skills));
                        break;
                    case ("EXPERIENCE"):
                        Map<String, String> experienceReference = new HashMap<>();
                        experienceReference.put("careers", sectionTypeName);
                        experienceReference.put("positions", "experiencePosition");
                        experienceReference.put("urls", "experienceCareerUrl");
                        experienceReference.put("startDates", "experiencePositionStartDate");
                        experienceReference.put("endDates", "experiencePositionEndDate");
                        experienceReference.put("descriptions", "experiencePositionDescription");
                        List<Career> careers = setCareers(resume, experienceReference, request);
                        resume.setSection(sectionType, new CareerSection(careers));
                        break;
                    case ("EDUCATION"):
                        Map<String, String> educationReference = new HashMap<>();
                        educationReference.put("careers", sectionTypeName);
                        educationReference.put("positions", "educationPosition");
                        educationReference.put("urls", "educationCareerUrl");
                        educationReference.put("startDates", "educationPositionStartDate");
                        educationReference.put("endDates", "educationPositionEndDate");
                        educationReference.put("descriptions", "educationPositionDescription");
                        List<Career> educationCareers = setCareers(resume, educationReference, request);
                        resume.setSection(sectionType, new CareerSection(educationCareers));
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
                forward(request, response, resume.getUuid(), "WEB-INF/jsp/edit.jsp");
                break;
            default:
                throw new IllegalStateException("Action " + action + "is illegal");
        }
    }

    private List<Career> setCareers(Resume resume, Map<String, String> reference, HttpServletRequest request) {
        String[] values = request.getParameterValues(reference.get("careers"));
        String[] careerPositions = request.getParameterValues(reference.get("positions"));
        String[] careerUrls = request.getParameterValues(reference.get("urls"));
        String[] startDates = request.getParameterValues(reference.get("startDates"));
        String[] endDates = request.getParameterValues(reference.get("endDates"));
        String[] descriptions = request.getParameterValues(reference.get("descriptions"));
        List<Career> careers = new ArrayList<>();
        int newCareerSize = values.length;
        for (int i = 0; i < newCareerSize; i++) {
            List<Career.Position> positions = new ArrayList<>();
            if (!careerPositions[i].trim().isEmpty() && !startDates[i].trim().isEmpty()) {
                LocalDate startDate = DateUtil.of(startDates[i]);
                LocalDate endDate = DateUtil.of(endDates[i]);
                Career.Position position = new Career.Position(careerPositions[i], startDate, endDate);
                positions.add(position);

                String title = values[i];
                Link link = new Link(title);
                String url = careerUrls[i];
                if (!url.trim().isEmpty()) {
                    link.setUrl(url);
                }
                String description = descriptions[i];
                if (!description.trim().isEmpty()) {
                    position.setDescription(description);
                }
                Career career = new Career(link, positions);
                careers.add(career);
            } else {
                newCareerSize--;
            }
        }
        CareerSection careerSection = ((CareerSection) resume.getSection(SectionType.valueOf(reference.get("careers"))));
        if (careerSection != null) {
            int careerSize = careerSection.getElement().size();
            if (careerSize < newCareerSize) {
                Collections.rotate(careers, newCareerSize + 1);
            }
        }
        return careers;
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String uuid, String jsp) throws ServletException, IOException {
        Resume resume = sqlStorage.get(uuid);
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(jsp).forward(request, response);
    }
}