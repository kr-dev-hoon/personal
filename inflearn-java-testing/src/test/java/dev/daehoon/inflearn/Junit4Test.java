package dev.daehoon.inflearn;

import org.junit.Before;
import org.junit.Test;

public class Junit4Test {

    @Before
    void setup() {

        System.out.println("setup");
    }

    @Test
    void test() {

        System.out.println("test");
    }
}
