package com.example.interviewchallenge.core.user.usecase

import arrow.core.left
import arrow.core.right
import com.example.interviewchallenge.core.user.model.CreateUserDomain
import com.example.interviewchallenge.core.user.model.UserDomain
import com.example.interviewchallenge.core.user.model.UserError
import com.example.interviewchallenge.core.user.port.UserPort
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.UUID

class CreateUserUseCaseTest {
    private val userPort: UserPort = mockk()
    private val useCase = CreateUserUseCase(userPort)

    @Test
    fun `creates user successfully`() {
        val createDomain = CreateUserDomain(email = "jane@example.com")
        val created = UserDomain(
            id = UUID.randomUUID(),
            email = "jane@example.com",
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
        )
        every { userPort.create(createDomain) } returns created.right()

        val result = useCase(createDomain)

        assertTrue(result.isRight())
        assertEquals(created, result.getOrNull())
    }

    @Test
    fun `returns error on persistence failure`() {
        val createDomain = CreateUserDomain(email = "jane@example.com")
        every { userPort.create(createDomain) } returns UserError.PERSISTENCE_ERROR.left()

        val result = useCase(createDomain)

        assertTrue(result.isLeft())
        assertEquals(UserError.PERSISTENCE_ERROR, result.leftOrNull())
    }
}
