package dev.daehoon.inflearn;

import dev.daehoon.inflearn.domain.Study;
import dev.daehoon.inflearn.study.StudyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

@SpringBootTest(classes = StudyRepository.class)
@Testcontainers
@Slf4j
@ContextConfiguration(initializers = StudyServiceTest.ContainerContextPropertyInitializer.class)
public class StudyServiceTest {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    Environment environment;

    @Value("${containger.port}") int port;

    @Container
    static GenericContainer postgresSqlContainer = new GenericContainer("postgres")
            .withExposedPorts(5432)
            .withEnv("POSTGRES_DB", "study_db");

    @BeforeAll
    static void beforeAll() {

        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
        postgresSqlContainer.followOutput(logConsumer);
    }

    @BeforeEach
    void beforeEach() {

        studyRepository.deleteAll();
    }

    @Test
    public void findByIdTest() {

        Optional<Study> study = studyRepository.findById(1);

        System.out.println(postgresSqlContainer.getLogs());

    }

    static class ContainerContextPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            TestPropertyValues.of("container.port="+postgresSqlContainer.getMappedPort(5432))
            .applyTo(configurableApplicationContext.getEnvironment());

        }
    }

}
