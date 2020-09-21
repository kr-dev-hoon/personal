package dev.daehoon.inflearn.mockito;

import dev.daehoon.inflearn.domain.Study;
import dev.daehoon.inflearn.study.StudyRepository;
import dev.daehoon.inflearn.study.StudyService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) // MOCKING이 필요없는 코드에서 Mocking시도시 추가하고 테스트 진행해야함.
// 공부를 위해 해당 settings를 추가하고 진행.
public class BasicTest {

    @Mock
    private StudyRepository studyRepository;

    // 외부 API, Database와 연동해서 Test를 진행해야하는 경우 이런 환경과 연동하는 경우
    // Mock 객체로 만들고, 해당 객체의 동작을 정의하고 비즈니스 로직을 테스트.
    // 제어하기 힘든 외부 환경의 경우는 mock을 사용하는게 편하다.

    // https://github.com/mockito/mockito/wiki/Mockito-features-in-Korean

    // given-when-then의 방식의 BDD

    //    Mockito.when(mock.action()).thenReturn(true) << when이라 좀 햇갈린다.
    //    BDDMockito.given(mock.action()).willReturn(true)

    @Test
    void study_repository_test() {

        StudyService studyService = new StudyService(studyRepository);

        //given
        when(studyRepository.findAllById(Arrays.asList(1L))).thenReturn(Arrays.asList(new Study(10, "TITLE")));

        //        when(studyRepository.findAllById(any())).thenReturn(Arrays.asList(new Study(10, "TITLE")));
        //        when(studyRepository.findAllById(any())).thenThrow(RuntimeException.class);

        when(studyRepository.findById(1)).thenReturn(Optional.ofNullable(new Study(10, "TITLE")));

        doThrow(new RuntimeException((""))).when(studyRepository).validate(1);

        assertThrows(RuntimeException.class, () -> {
            studyRepository.validate(1L);
        });
        //when
        Study actual = studyRepository.findAllById(Arrays.asList(1L)).get(0);
        //then
        Assert.assertEquals(actual.getId(), 10);
        Assert.assertEquals(actual.getTitle(), "TITLE");

        studyService.findById(1);

        verify(studyRepository, times(1)).notify(1);

        verify(studyRepository, Mockito.never()).notify2(1);

        InOrder inOrder = Mockito.inOrder(studyRepository);
        inOrder.verify(studyRepository).validate(1L);
        inOrder.verify(studyRepository).notify(1);
    }
}
