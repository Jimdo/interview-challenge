package com.example.interviewchallenge.adapter.website.controller.rest

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class WebsiteCreateRequest(
    @field:NotNull
    val userId: UUID,
)
