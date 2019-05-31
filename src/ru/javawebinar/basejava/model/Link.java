package ru.javawebinar.basejava.model;

import java.util.Objects;

public class Link {
    private final String title;
    private String url;

    public Link(String title) {
        this(title, null);
    }

    public Link(String title, String url) {
        Objects.requireNonNull(title, "company or institution name can't be null");
        this.title = title;
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return title.equals(link.title) &&
                Objects.equals(url, link.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url);
    }
}