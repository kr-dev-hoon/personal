package dev.daehoon.inflearn;

import dev.daehoon.inflearn.study.StudyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

@SpringBootTest(classes = StudyRepository.class)
@Testcontainers
@Slf4j
public class StudyDockerComposeServiceTest {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    Environment environment;

    @Value("${containger.port}")
    int port;

    @Container
    static DockerComposeContainer dockerComposeContainer = new DockerComposeContainer(new File("/src/test/resources/docker-compose.yml"));

    @BeforeAll
    static void beforeAll() {

    }

    @BeforeEach
    void beforeEach() {

        studyRepository.deleteAll();
    }

    @Test
    public void findByIdTest() {

    }

}
