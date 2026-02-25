package com.example.interviewchallenge.core.domain.usecase

import com.example.interviewchallenge.core.domain.model.DomainDomain
import com.example.interviewchallenge.core.domain.port.DomainPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ListDomainsUseCase(
    private val domainPort: DomainPort,
) {
    operator fun invoke(websiteId: UUID): List<DomainDomain> =
        domainPort.findByWebsiteId(websiteId)
}
