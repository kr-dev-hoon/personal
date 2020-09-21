package dev.daehoon.inflearn.study;

import dev.daehoon.inflearn.domain.Study;
import org.springframework.stereotype.Service;

@Service
public class StudyService {

    private final StudyRepository studyRepository;

    public StudyService(StudyRepository studyRepository) {

        this.studyRepository = studyRepository;
    }

    public Study findById(long id) {

        studyRepository.notify(1);

        return studyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(""));
    }
}
