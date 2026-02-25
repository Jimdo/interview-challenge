package com.example.interviewchallenge.architecture

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.library.Architectures.layeredArchitecture
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AnalyzeClasses(
    packages = ["com.example.interviewchallenge"],
    importOptions = [ImportOption.DoNotIncludeTests::class],
)
internal class CleanCodeArchitectureTest {
    @ArchTest
    fun cleanCodeLayers(classes: JavaClasses) {
        layeredArchitecture()
            .consideringAllDependencies()
            .layer("Controller")
            .definedBy("com.example.interviewchallenge.adapter.*.controller..")
            .layer("Persistence")
            .definedBy("com.example.interviewchallenge.adapter.*.persistence..")
            .layer("PersistenceEntity")
            .definedBy("com.example.interviewchallenge.adapter.*.persistence.entity..")
            .layer("PersistenceRepository")
            .definedBy("com.example.interviewchallenge.adapter.*.persistence.impl..")
            .layer("PortImplementation")
            .definedBy("com.example.interviewchallenge.adapter.*.impl..")
            .layer("UseCase")
            .definedBy("com.example.interviewchallenge.core.*.usecase..")
            .layer("Port")
            .definedBy("com.example.interviewchallenge.core.*.port..")
            .whereLayer("Controller")
            .mayNotBeAccessedByAnyLayer()
            .whereLayer("PersistenceEntity")
            .mayOnlyBeAccessedByLayers("Persistence")
            .whereLayer("PersistenceRepository")
            .mayNotBeAccessedByAnyLayer()
            .whereLayer("PortImplementation")
            .mayNotBeAccessedByAnyLayer()
            .whereLayer("UseCase")
            .mayOnlyBeAccessedByLayers("Controller")
            .whereLayer("Port")
            .mayOnlyBeAccessedByLayers("UseCase", "PersistenceRepository", "PortImplementation")
            .withOptionalLayers(true)
            .check(classes)
    }
}
