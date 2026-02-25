package com.example.interviewchallenge.core.websiteversion.model

import java.time.Instant
import java.util.UUID

data class WebsiteVersionDomain(
    val id: UUID? = null,
    val websiteId: UUID,
    val versionNumber: Int,
    val payload: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)
