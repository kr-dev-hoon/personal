package dev.daehoon.inflearn;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.util.Properties;

// 현재 properties 파일의 일부 value 에 따라 test skip
public class EnvironmentExtension implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(
            ExtensionContext context) {

        Properties props = new Properties();
        try {
            props.load(EnvironmentExtension.class
                    .getResourceAsStream("../../../application-test.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String env = props.getProperty("env");
        if ("qa".equalsIgnoreCase(env)) {
            return ConditionEvaluationResult
                    .disabled("Test disabled on QA environment");
        }

        return ConditionEvaluationResult.enabled(
                "Test enabled on QA environment");
    }
}