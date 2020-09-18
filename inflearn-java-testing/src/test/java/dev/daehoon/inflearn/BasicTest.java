package dev.daehoon.inflearn;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BasicTest {

    @Test
    void create() {

        Basic basic = new Basic();
        assertNotNull(basic);
    }

    @BeforeAll
    public static void beforeAll() {
        // 모든 테스트가 실행되기 이전에 수행되는 메소드
        // static으로 선언되어야 합니다.
        // junit4 :: @BeforeClass

        System.out.println("BeforeAll");
    }

    @AfterAll
    public static void afterAll() {
        // 모든 테스트가 실행된 이후에 수행되는 메소드
        // static으로 선언되어야 합니다.
        // junit4 :: @AfterClass

        System.out.println("AfterAll");
    }

    @BeforeEach
    public void beforeEach() {
        // 개별 테스트 실행전에 한번씩 수행되는 메소드
        // junit4 :: @Before

        System.out.println("BeforeEach");
    }

    @AfterEach
    public void afterEach() {
        // 개별 테스트 실행후에 한번씩 수행되는 메소드
        // junit4 :: @After

        System.out.println("AfterEach");
    }

    @Test
    @Disabled
    public void disable() {
        // Test 비활성화

        System.out.println("Disable");
    }
}