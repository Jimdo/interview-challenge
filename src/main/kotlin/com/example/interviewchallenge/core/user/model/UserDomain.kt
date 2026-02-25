package com.example.interviewchallenge.core.user.model

import java.time.Instant
import java.util.UUID

data class UserDomain(
    val id: UUID? = null,
    val email: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)
