package dev.daehoon.inflearn;

import dev.daehoon.inflearn.study.Basic;
import dev.daehoon.inflearn.study.Status;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

// 선언
@ExtendWith(ExtendTest.class)
public class AssertionTest {

//    @RegisterExtension
//    static ExtendTest extendTest = new ExtendTest(2000L);

    @Test
    @Tag("fast")
    @SlowAnnotation
    public void assertTest() {

        Basic basic = new Basic();

        assertEquals(basic.getStatus(), Status.INPROGRESS, "Status 기본 값은 inprogress 이다.");

        assertTrue(1 > 0);

        // 객체 자체를 비교
        assertSame(basic, basic);

        assertNotSame(new Basic(), new Basic());

        Exception e = assertThrows(Exception.class, () -> basic.throwException());

        assertEquals(e.getMessage(), "Result Message");

        assertThat(10)
                .isEqualTo(10);

    }

    @Test
    @Tag("fast")
    public void assertAllTest() {

        // 동시 실행후 여러 결과를 받음.
        assertAll(() -> assertTrue(true),
                () -> assertTrue(11 > 0),
                () -> assertTrue(111 > 0));

    }

    @Test
    @Tag("slow")
    public void assertTimeoutTest() {
        // 10초안에 성공하지 않으면 실패
        assertTimeout(Duration.ofSeconds(10), () -> new Basic());
    }
}
