package com.example.interviewchallenge.core.website.usecase

import arrow.core.Either
import arrow.core.left
import com.example.interviewchallenge.core.user.port.UserPort
import com.example.interviewchallenge.core.website.model.CreateWebsiteDomain
import com.example.interviewchallenge.core.website.model.WebsiteDomain
import com.example.interviewchallenge.core.website.model.WebsiteError
import com.example.interviewchallenge.core.website.port.WebsitePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateWebsiteUseCase(
    private val websitePort: WebsitePort,
    private val userPort: UserPort,
) {
    @Transactional
    operator fun invoke(createWebsite: CreateWebsiteDomain): Either<WebsiteError, WebsiteDomain> =
        userPort.findById(createWebsite.userId).fold(
            { WebsiteError.USER_NOT_FOUND.left() },
            { websitePort.create(createWebsite) },
        )
}
