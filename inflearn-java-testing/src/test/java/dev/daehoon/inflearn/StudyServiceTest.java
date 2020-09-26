package dev.daehoon.inflearn;

import dev.daehoon.inflearn.domain.Study;
import dev.daehoon.inflearn.study.StudyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

@SpringBootTest(classes = StudyRepository.class)
@Testcontainers
public class StudyServiceTest {

    @Autowired
    private StudyRepository studyRepository;

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
            .withDatabaseName("/studytest");

    @BeforeEach
    void beforeEach() {

        studyRepository.deleteAll();
    }

    @Test
    public void findByIdTest() {

        Optional<Study> study = studyRepository.findById(1);

    }

}
