package com.example.interviewchallenge.core.domain.port

import arrow.core.Either
import com.example.interviewchallenge.core.domain.model.CreateDomainDomain
import com.example.interviewchallenge.core.domain.model.DomainDomain
import com.example.interviewchallenge.core.domain.model.DomainError
import java.util.UUID

interface DomainPort {
    fun findByWebsiteId(websiteId: UUID): List<DomainDomain>

    fun findByDomain(domain: String): Either<DomainError, DomainDomain>

    fun create(domain: CreateDomainDomain): Either<DomainError, DomainDomain>

    fun delete(domain: String): Either<DomainError, Unit>
}
