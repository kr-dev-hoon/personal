package dev.daehoon.inflearn;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;

// 기본 기능 이외에 확장 기능을 사용하기 위함. Extension.
// test instance, 에러 처리, ..
// https://www.baeldung.com/junit-5-extensions
public class ExtendTest implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private long THRESHOLD = 1000L;
//
//    public ExtendTest(long THRESHOLD) {
//
//        this.THRESHOLD = THRESHOLD;
//    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {

        String testClassName = extensionContext.getRequiredTestClass().getName();
        String testMethodName = extensionContext.getRequiredTestMethod().getName();
        ExtensionContext.Store store = extensionContext
                .getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));

        store.put("start_time", System.currentTimeMillis());

    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {

        String testClassName = extensionContext.getRequiredTestClass().getName();
        String testMethodName = extensionContext.getRequiredTestMethod().getName();

        ExtensionContext.Store store = extensionContext
                .getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));

        Method method = extensionContext.getRequiredTestMethod();

        SlowAnnotation slowAnnotation = method.getAnnotation(SlowAnnotation.class);

        long start_time = store.remove("start_time", long.class);
        long duration = System.currentTimeMillis() - start_time;
        if (duration > THRESHOLD && slowAnnotation == null) {
            System.out.printf("Please Consider Mark [%s] Method Slow", testMethodName);
        }
    }

}
