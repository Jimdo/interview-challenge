package com.example.interviewchallenge.application.persistence

import org.flywaydb.core.Flyway
import org.testcontainers.postgresql.PostgreSQLContainer

internal class TestingPostgreSQLContainer private constructor() :
    PostgreSQLContainer(IMAGE_VERSION) {

    override fun start() {
        super.start()
        System.setProperty("spring.datasource.url", instance.jdbcUrl)
        System.setProperty("spring.datasource.username", instance.username)
        System.setProperty("spring.datasource.password", instance.password)

        val flyway =
            Flyway
                .configure()
                .schemas("public")
                .locations("classpath:db/migration")
                .dataSource(instance.jdbcUrl, instance.username, instance.password)
                .load()

        flyway.migrate()
    }

    override fun stop() {
        // do nothing, JVM handles shut down
    }

    companion object {
        private const val IMAGE_VERSION = "postgres:16"
        private val container: TestingPostgreSQLContainer = TestingPostgreSQLContainer()
        val instance: TestingPostgreSQLContainer
            get() {
                if (!container.isRunning) {
                    container.start()
                }
                return container
            }
    }
}

abstract class PostgreSqlContainerTest {
    companion object {
        private val database = TestingPostgreSQLContainer.instance
    }
}
