package com.example.interviewchallenge.application

import com.example.interviewchallenge.application.persistence.TestingPostgreSQLContainer
import org.junit.jupiter.api.Test
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer

@SpringBootTest
@DirtiesContext
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
internal class ApplicationTest {
    companion object {
        @JvmField
        @Container
        val postgreSQLContainer: PostgreSQLContainer = TestingPostgreSQLContainer.instance
    }

    @Test
    fun contextLoads() {
    }
}
