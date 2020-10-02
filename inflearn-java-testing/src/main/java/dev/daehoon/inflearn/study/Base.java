package dev.daehoon.inflearn.study;

public class Base {

    Integer integer;

    String name;

    public Base(Integer integer) {

        this.integer = integer;
    }

    public Base(Integer integer, String name) {

        this.integer = integer;
        this.name = name;
    }

    public Integer getInteger() {

        return integer;
    }

    public String getName() {

        return name;
    }
}
