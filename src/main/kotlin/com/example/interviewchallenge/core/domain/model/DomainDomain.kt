package com.example.interviewchallenge.core.domain.model

import java.time.Instant
import java.util.UUID

data class DomainDomain(
    val domain: String,
    val websiteId: UUID,
    val createdAt: Instant,
    val updatedAt: Instant,
)
