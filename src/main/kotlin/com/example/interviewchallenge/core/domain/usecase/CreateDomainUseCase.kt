package com.example.interviewchallenge.core.domain.usecase

import arrow.core.Either
import arrow.core.left
import com.example.interviewchallenge.core.domain.model.CreateDomainDomain
import com.example.interviewchallenge.core.domain.model.DomainDomain
import com.example.interviewchallenge.core.domain.model.DomainError
import com.example.interviewchallenge.core.domain.port.DomainPort
import com.example.interviewchallenge.core.website.port.WebsitePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateDomainUseCase(
    private val domainPort: DomainPort,
    private val websitePort: WebsitePort,
) {
    @Transactional
    operator fun invoke(createDomain: CreateDomainDomain): Either<DomainError, DomainDomain> =
        websitePort.findById(createDomain.websiteId).fold(
            { DomainError.WEBSITE_NOT_FOUND.left() },
            { domainPort.create(createDomain) },
        )
}
