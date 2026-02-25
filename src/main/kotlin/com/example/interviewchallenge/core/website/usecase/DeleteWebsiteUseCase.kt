package com.example.interviewchallenge.core.website.usecase

import arrow.core.Either
import com.example.interviewchallenge.core.website.model.WebsiteError
import com.example.interviewchallenge.core.website.port.WebsitePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class DeleteWebsiteUseCase(
    private val websitePort: WebsitePort,
) {
    @Transactional
    operator fun invoke(id: UUID): Either<WebsiteError, Unit> =
        websitePort.delete(id)
}
