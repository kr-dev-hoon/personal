package dev.daehoon.inflearn;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class ArchTest {

    @Test
    public void Services_should_only_be_accessed_by_controllers() {

        JavaClasses javaClasses = new ClassFileImporter().importPackages("dev.daehoon.inflearn");

        ArchRule myRule = classes()
                .that().resideInAPackage("..service..")
                .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..");

        myRule.check(javaClasses);

    }

    @Test
    void packageDependencyTests() {

        JavaClasses javaClasses = new ClassFileImporter().importPackages("dev.daehoon.inflearn");

        ArchRule domainPackageRule = classes()
                .that().resideInAPackage("..domain..")
                .should().onlyBeAccessed().byClassesThat()
                .resideInAnyPackage("..study..", "..member..", "..domain..","..mockito..");

        domainPackageRule.check(javaClasses);

        ArchRule memberPackageRule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().onlyBeAccessed().byClassesThat()
                .resideInAPackage("..member..");

        memberPackageRule.check(javaClasses);

        ArchRule studyPackageRule = classes()
                .that().resideInAPackage("..study..")
                .should().onlyBeAccessed().byClassesThat().resideInAnyPackage("..study..", "..mockito..","dev.daehoon.inflearn");

        studyPackageRule.check(javaClasses);

        // 순환참조 체크.
        ArchRule freeOfCycles = slices().matching("dev.daehoon.inflearn")
                .should().beFreeOfCycles();

        freeOfCycles.check(javaClasses);

    }
}
