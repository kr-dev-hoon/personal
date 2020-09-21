package dev.daehoon.inflearn.mockito;

import dev.daehoon.inflearn.domain.Study;
import dev.daehoon.inflearn.study.StudyRepository;
import dev.daehoon.inflearn.study.StudyService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StudyServiceTest {

    @Mock
    private StudyRepository studyRepository;

    @Test
    public void findByIdTest() {
        //        public void findByIdTest(@Mock StudyRepository studyRepository) {

        Optional<Study> study = studyRepository.findById(1);

        Assert.assertEquals(Optional.empty(), study);

        studyRepository.validate(1);

        StudyService studyService = new StudyService(studyRepository);
        //        StudyRepository studyRepository = Mockito.mock(StudyRepository.class);

    }

    @Test
    public void findByIdMockTest() {

        Study study = new Study(1, "Title");

        when(studyRepository.findById(1)).thenReturn(Optional.ofNullable(study));

        StudyService studyService = new StudyService(studyRepository);
        //        StudyRepository studyRepository = Mockito.mock(StudyRepository.class);

    }
}
