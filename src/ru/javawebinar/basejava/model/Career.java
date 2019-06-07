package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * This class describes EXPERIENCE("Опыт работы") and EDUCATION("Образование") Sections in Resume.
 */
public class Career {
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

    public Career(String position, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(position, "position or career can't be null");
        Objects.requireNonNull(startDate, "startDate can't be null");
        Objects.requireNonNull(endDate, "endDate can't be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return position.equals(career.position) &&
                startDate.equals(career.startDate) &&
                endDate.equals(career.endDate) &&
                Objects.equals(description, career.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, startDate, endDate, description);
    }
}