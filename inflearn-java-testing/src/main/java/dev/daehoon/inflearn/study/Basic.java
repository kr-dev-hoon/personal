package dev.daehoon.inflearn.study;

public class Basic {

    Status status;

    public Basic() {

        this.status = Status.INPROGRESS;
    }

    public Status getStatus() {

        return status;
    }

    public void throwException() throws Exception {

        throw new Exception("Result Message");
    }
}
