package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class stores Position instances of EXPERIENCE("Опыт работы") and EDUCATION("Образование") Sections in Resume.
 */
public class Career {
    private Link homePage;
    private List<Position> positions = new ArrayList<>();

    public Career(String title) {
        this(title, null);
    }

    public Career(String title, String url) {
        Objects.requireNonNull(title, "Company or institution name can't be null");
        this.homePage = new Link(title, url);
    }

    public Career(String title, String url, Position... position) {
        this(new Link(title, url), Arrays.asList(position));
    }

    public Career(Link link, List<Position> positions) {
        Objects.requireNonNull(link, "Company or institution link can't be null");
        Objects.requireNonNull(positions, "List of positions in company or institution can't be null");
        this.homePage = link;
        this.positions = positions;
    }

    public void addPosition(String title, Position position) {
        Objects.requireNonNull(title, "Company or institution title can't be null");
        Objects.requireNonNull(position, "Career element can't be null");
        if (!this.homePage.getTitle().equals(title)) {
            throw new RuntimeException("Can't add position with Company or institution name not equal to existing");
        }
        this.positions.add(position);
    }

    public void setUrl(String url) {
        this.homePage.setUrl(url);
    }

    public List<Position> getPositions() {
        return positions;
    }

    public String getTitle() {
        return homePage.getTitle();
    }

    public String getUrl() {
        return homePage.getUrl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Career career = (Career) o;
        return homePage.equals(career.homePage) &&
                positions.equals(career.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, positions);
    }

    /**
     * This class describes EXPERIENCE("Опыт работы") and EDUCATION("Образование") Sections in Resume.
     */
    public static class Position {
        private String position;
        private LocalDate startDate;
        private LocalDate endDate;
        private String description;

        public Position(String position, int startYear, int startMonth) {
            this(position, DateUtil.of(startYear, startMonth), DateUtil.NOW);
        }

        public Position(String position, int startYear, int startMonth, int endYear, int endMonth) {
            this(position, DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth));
        }

        public Position(String position, LocalDate startDate, LocalDate endDate) {
            Objects.requireNonNull(position, "Position in career can't be null");
            Objects.requireNonNull(startDate, "StartDate can't be null");
            Objects.requireNonNull(endDate, "EndDate can't be null");
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
            Position position1 = (Position) o;
            return position.equals(position1.position) &&
                    startDate.equals(position1.startDate) &&
                    endDate.equals(position1.endDate) &&
                    Objects.equals(description, position1.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, startDate, endDate, description);
        }
    }
}
