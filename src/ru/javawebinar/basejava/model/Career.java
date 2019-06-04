package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * This class describes EXPERIENCE("Опыт работы") and EDUCATION("Образование") Sections in Resume.
 */
public class Career {
    private Link homePage;

    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

    public Career(String title, String position, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(title, "company or institution name can't be null");
        Objects.requireNonNull(position, "position or career can't be null");
        Objects.requireNonNull(startDate, "startDate can't be null");
        Objects.requireNonNull(endDate, "endDate can't be null");
        this.homePage = new Link(title);
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.homePage.setUrl(url);
    }


    public String getTitle() {
        return homePage.getTitle();
    }

    public String getUrl() {
        return homePage.getUrl();
    }

    public Link getHomePage() {
        return homePage;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Career career = (Career) o;
        return homePage.equals(career.homePage) &&
                position.equals(career.position) &&
                startDate.equals(career.startDate) &&
                endDate.equals(career.endDate) &&
                Objects.equals(description, career.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, position, startDate, endDate, description);
    }

}