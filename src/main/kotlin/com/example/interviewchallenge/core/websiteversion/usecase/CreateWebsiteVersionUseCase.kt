package com.example.interviewchallenge.core.websiteversion.usecase

import arrow.core.Either
import arrow.core.left
import com.example.interviewchallenge.core.website.port.WebsitePort
import com.example.interviewchallenge.core.websiteversion.model.CreateWebsiteVersionDomain
import com.example.interviewchallenge.core.websiteversion.model.WebsiteVersionDomain
import com.example.interviewchallenge.core.websiteversion.model.WebsiteVersionError
import com.example.interviewchallenge.core.websiteversion.port.WebsiteVersionPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class CreateWebsiteVersionUseCase(
    private val websiteVersionPort: WebsiteVersionPort,
    private val websitePort: WebsitePort,
) {
    @Transactional
    operator fun invoke(createVersion: CreateWebsiteVersionDomain): Either<WebsiteVersionError, WebsiteVersionDomain> =
        websitePort.findById(createVersion.websiteId).fold(
            { WebsiteVersionError.WEBSITE_NOT_FOUND.left() },
            {
                val existingVersions = websiteVersionPort.findByWebsiteId(createVersion.websiteId)
                val nextVersionNumber = (existingVersions.maxOfOrNull { it.versionNumber } ?: 0) + 1
                websiteVersionPort.create(createVersion, nextVersionNumber)
            },
        )
}
