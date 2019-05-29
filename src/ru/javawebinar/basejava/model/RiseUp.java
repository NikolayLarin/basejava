package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * This class describes EXPERIENCE("Опыт работы") and EDUCATION("Образование") Sections in Resume.
 */
public class RiseUp {
    private LocalDate startDate;
    private LocalDate endDate;
    private String position;
    private String description;


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/YYYY");

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDescription(String description) {
        this.description = description;
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
        RiseUp that = (RiseUp) o;
        return startDate.equals(that.startDate) &&
                endDate.equals(that.endDate) &&
                position.equals(that.position) &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, position, description);
    }
}