package com.example.interviewchallenge.core.domain.model

import java.util.UUID

data class CreateDomainDomain(
    val domain: String,
    val websiteId: UUID,
)
