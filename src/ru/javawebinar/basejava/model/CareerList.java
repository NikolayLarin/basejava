package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class stores Career instances of EXPERIENCE("Опыт работы") and EDUCATION("Образование") Sections in Resume.
 */
public class CareerList {
    private Link homePage;
    private List<Career> careerList = new ArrayList<>();

    public CareerList(String title, Career career) {
        Objects.requireNonNull(title, "company or institution name can't be null");
        Objects.requireNonNull(career, "career element can't be null");
        this.homePage = new Link(title);
        this.careerList.add(career);
    }

    public List<Career> getCareerList() {
        return careerList;
    }

    public void addCareer(String title, Career career) {
        Objects.requireNonNull(title, "company or institution name can't be null");
        Objects.requireNonNull(career, "career element can't be null");
        if (this.homePage.getTitle().equals(title)) {
            this.careerList.add(career);
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CareerList that = (CareerList) o;
        return homePage.equals(that.homePage) &&
                careerList.equals(that.careerList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, careerList);
    }
}
