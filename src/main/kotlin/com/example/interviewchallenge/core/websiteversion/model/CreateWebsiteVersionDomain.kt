package com.example.interviewchallenge.core.websiteversion.model

import java.util.UUID

data class CreateWebsiteVersionDomain(
    val websiteId: UUID,
    val payload: String,
)
