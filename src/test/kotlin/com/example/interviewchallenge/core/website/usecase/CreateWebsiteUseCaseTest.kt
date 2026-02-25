package com.example.interviewchallenge.core.website.usecase

import arrow.core.left
import arrow.core.right
import com.example.interviewchallenge.core.user.model.UserDomain
import com.example.interviewchallenge.core.user.model.UserError
import com.example.interviewchallenge.core.user.port.UserPort
import com.example.interviewchallenge.core.website.model.CreateWebsiteDomain
import com.example.interviewchallenge.core.website.model.WebsiteDomain
import com.example.interviewchallenge.core.website.model.WebsiteError
import com.example.interviewchallenge.core.website.port.WebsitePort
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.UUID

class CreateWebsiteUseCaseTest {
    private val websitePort: WebsitePort = mockk()
    private val userPort: UserPort = mockk()
    private val useCase = CreateWebsiteUseCase(websitePort, userPort)

    @Test
    fun `creates website when user exists`() {
        val userId = UUID.randomUUID()
        val createDomain = CreateWebsiteDomain(userId = userId)
        val user = UserDomain(
            id = userId,
            email = "jane@example.com",
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
        )
        val created = WebsiteDomain(
            id = UUID.randomUUID(),
            userId = userId,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
        )
        every { userPort.findById(userId) } returns user.right()
        every { websitePort.create(createDomain) } returns created.right()

        val result = useCase(createDomain)

        assertTrue(result.isRight())
        assertEquals(created, result.getOrNull())
    }

    @Test
    fun `returns error when user not found`() {
        val userId = UUID.randomUUID()
        val createDomain = CreateWebsiteDomain(userId = userId)
        every { userPort.findById(userId) } returns UserError.USER_NOT_FOUND.left()

        val result = useCase(createDomain)

        assertTrue(result.isLeft())
        assertEquals(WebsiteError.USER_NOT_FOUND, result.leftOrNull())
        verify(exactly = 0) { websitePort.create(any()) }
    }
}
