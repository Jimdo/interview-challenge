package com.example.interviewchallenge.core.websiteversion.usecase

import com.example.interviewchallenge.core.websiteversion.model.WebsiteVersionDomain
import com.example.interviewchallenge.core.websiteversion.port.WebsiteVersionPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ListWebsiteVersionsUseCase(
    private val websiteVersionPort: WebsiteVersionPort,
) {
    operator fun invoke(websiteId: UUID): List<WebsiteVersionDomain> =
        websiteVersionPort.findByWebsiteId(websiteId)
}
