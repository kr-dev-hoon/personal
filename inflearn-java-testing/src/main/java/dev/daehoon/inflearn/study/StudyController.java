package dev.daehoon.inflearn.study;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/study")
public class StudyController {

    StudyRepository studyRepository;

    StudyService studyService;

    public StudyController(StudyRepository studyRepository, StudyService studyService) {

        this.studyRepository = studyRepository;
        this.studyService = studyService;
    }
}
