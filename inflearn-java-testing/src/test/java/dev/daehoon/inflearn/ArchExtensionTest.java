package dev.daehoon.inflearn;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packagesOf = App.class)
public class ArchExtensionTest {

    @ArchTest
    ArchRule domainPackageRule = classes()
            .that().resideInAPackage("..domain..")
            .should().onlyBeAccessed().byClassesThat()
            .resideInAnyPackage("..study..", "..member..", "..domain..", "..mockito..");

    @ArchTest
    ArchRule studyPackageRule = classes()
            .that().resideInAPackage("..study..")
            .should().onlyBeAccessed().byClassesThat()
            .resideInAnyPackage("..study..", "..mockito..", "dev.daehoon.inflearn");

    @ArchTest
    ArchRule memberPackageRule = noClasses()
            .that().resideInAPackage("..domain..")
            .should().onlyBeAccessed().byClassesThat()
            .resideInAPackage("..member..");

    @ArchTest
    ArchRule freeOfCycles = slices().matching("dev.daehoon.inflearn")
            .should().beFreeOfCycles();
}
