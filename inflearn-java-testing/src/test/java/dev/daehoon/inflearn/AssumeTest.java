package dev.daehoon.inflearn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

public class AssumeTest {

    @Test
    void envTest() {

        assumeTrue("C:\\Program Files\\Java\\jdk1.8.0_221".equals(System.getenv("JAVA_HOME")));

        System.out.println("After assumeTrue");

        assumingThat("C:\\Program Files\\Java\\jdk1.8.0_221".equals(System.getenv("JAVA_HOME")), () ->
                System.out.println("After assumingThat")
        );

        assumeFalse("C:\\Program Files\\Java\\jdk1.8.0_221".equals(System.getenv("JAVA_HOME")));

        System.out.println("After assumeFalse");
    }

    @Test
    @EnabledOnOs(OS.MAC)
    public void onlyMacTest() {
        System.out.println("ONLY MAC");
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void onlyWindowsTest() {
        System.out.println("ONLY WINDOWS");
    }

    @Test
    @EnabledOnJre({ JRE.JAVA_8})
    public void onlyJre8AndJre11Test() {
        System.out.println("ONLY Jre8, 11");
    }
}
