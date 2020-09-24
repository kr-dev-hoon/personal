package dev.daehoon.inflearn.testcontainer;

import dev.daehoon.inflearn.domain.Study;
import dev.daehoon.inflearn.study.StudyRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

@SpringBootTest(classes = StudyRepository.class)
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:/dev/daehoon/inflearn/application-test.properties")
public class StudyServiceTest {

    @Autowired
    private StudyRepository studyRepository;

    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer();

    @BeforeAll
    static void beforeAll() {

        postgreSQLContainer.start();
        System.out.println(postgreSQLContainer.getJdbcUrl());
    }

    @AfterAll
    static void afterAll() {

        postgreSQLContainer.stop();
    }

    @Test
    public void findByIdTest() {

        Optional<Study> study = studyRepository.findById(1);

    }

}
