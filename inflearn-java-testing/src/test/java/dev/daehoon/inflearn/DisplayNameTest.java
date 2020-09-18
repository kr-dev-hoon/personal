package dev.daehoon.inflearn;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

// Test의 이름의 under score를 white space로 변환한다.
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class DisplayNameTest {

    @Test
    @DisplayName("클래스 만들기 테스트")
    public void create_new_basic() {
        Basic basic = new Basic();
    }

}
