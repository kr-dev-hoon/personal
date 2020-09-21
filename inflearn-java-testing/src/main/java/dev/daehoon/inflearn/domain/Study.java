package dev.daehoon.inflearn.domain;

public class Study {

    long id;

    String title;

    public Study(long id, String title) {

        this.id = id;
        this.title = title;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }
}
