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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) // MOCKING이 필요없는 코드에서 Mocking시도시 추가하고 테스트 진행해야함.
public class BddTest {

    @Mock
    private StudyRepository studyRepository;

    // 행동에 대한 이해를 구성하는 방법론

    @Test
    public void bddTest() {

        StudyService studyService = new StudyService(studyRepository);

        //given
        given(studyRepository.findAllById(Arrays.asList(1L))).willReturn(Arrays.asList(new Study(10, "TITLE")));

        //        when(studyRepository.findAllById(any())).thenReturn(Arrays.asList(new Study(10, "TITLE")));
        //        when(studyRepository.findAllById(any())).thenThrow(RuntimeException.class);

        given(studyRepository.findById(1)).willReturn(Optional.ofNullable(new Study(10, "TITLE")));

        //when
        Study actual = studyRepository.findAllById(Arrays.asList(1L)).get(0);
        studyService.findById(1);
        //then
        Assert.assertEquals(actual.getId(), Long.valueOf(10));
        Assert.assertEquals(actual.getName(), "TITLE");

        then(studyRepository).should(times(1)).notify(1);

    }
}
