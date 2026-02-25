package com.example.interviewchallenge.core.website.usecase

import arrow.core.Either
import com.example.interviewchallenge.core.website.model.WebsiteDomain
import com.example.interviewchallenge.core.website.model.WebsiteError
import com.example.interviewchallenge.core.website.port.WebsitePort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetWebsiteUseCase(
    private val websitePort: WebsitePort,
) {
    operator fun invoke(id: UUID): Either<WebsiteError, WebsiteDomain> =
        websitePort.findById(id)
}
