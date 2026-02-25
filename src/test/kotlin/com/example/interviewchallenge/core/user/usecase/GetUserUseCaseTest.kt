package com.example.interviewchallenge.core.user.usecase

import arrow.core.left
import arrow.core.right
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

class GetUserUseCaseTest {
    private val userPort: UserPort = mockk()
    private val useCase = GetUserUseCase(userPort)

    @Test
    fun `returns user when found`() {
        val id = UUID.randomUUID()
        val user = UserDomain(
            id = id,
            email = "jane@example.com",
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
        )
        every { userPort.findById(id) } returns user.right()

        val result = useCase(id)

        assertTrue(result.isRight())
        assertEquals(user, result.getOrNull())
    }

    @Test
    fun `returns error when user not found`() {
        val id = UUID.randomUUID()
        every { userPort.findById(id) } returns UserError.USER_NOT_FOUND.left()

        val result = useCase(id)

        assertTrue(result.isLeft())
        assertEquals(UserError.USER_NOT_FOUND, result.leftOrNull())
    }
}
