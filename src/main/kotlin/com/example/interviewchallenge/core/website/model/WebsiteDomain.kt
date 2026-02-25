package com.example.interviewchallenge.core.website.model

import java.time.Instant
import java.util.UUID

data class WebsiteDomain(
    val id: UUID? = null,
    val userId: UUID,
    val createdAt: Instant,
    val updatedAt: Instant,
)
