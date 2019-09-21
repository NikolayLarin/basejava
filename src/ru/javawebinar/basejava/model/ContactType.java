package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE("Тел."),
    MOBILE("Moбильный"),
    HOME_PHONE("Домашний тел."),
    SKYPE("Skype") {
        @Override
        public String toHtmlLowLevel(String value) {
            return "<a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    EMAIL("Почта") {
        @Override
        public String toHtmlLowLevel(String value) {
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    SITE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtmlLowLevel(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return value == null ? "" : toHtmlLowLevel(value);
    }
}