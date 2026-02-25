package com.example.interviewchallenge.core.domain.usecase

import arrow.core.Either
import com.example.interviewchallenge.core.domain.model.DomainError
import com.example.interviewchallenge.core.domain.port.DomainPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteDomainUseCase(
    private val domainPort: DomainPort,
) {
    @Transactional
    operator fun invoke(domain: String): Either<DomainError, Unit> =
        domainPort.delete(domain)
}
