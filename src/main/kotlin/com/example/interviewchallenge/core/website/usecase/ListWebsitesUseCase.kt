package com.example.interviewchallenge.core.website.usecase

import com.example.interviewchallenge.core.website.model.WebsiteDomain
import com.example.interviewchallenge.core.website.port.WebsitePort
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ListWebsitesUseCase(
    private val websitePort: WebsitePort,
) {
    operator fun invoke(pageable: Pageable, userId: UUID? = null): Page<WebsiteDomain> =
        if (userId != null) {
            websitePort.findByUserId(userId, pageable)
        } else {
            websitePort.findAll(pageable)
        }
}
