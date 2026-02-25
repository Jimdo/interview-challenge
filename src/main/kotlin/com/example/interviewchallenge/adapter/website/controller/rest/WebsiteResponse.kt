package com.example.interviewchallenge.adapter.website.controller.rest

import java.time.Instant
import java.util.UUID

sealed class WebsiteReadResponse {
    data class WebsiteSuccessResponse(
        val id: UUID,
        val userId: UUID,
        val createdAt: Instant,
        val updatedAt: Instant,
    ) : WebsiteReadResponse()

    data class WebsiteErrorResponse(
        val error: WebsiteResponseError,
    ) : WebsiteReadResponse()
}

sealed class WebsiteListResponse {
    data class WebsiteListSuccessResponse(
        val websites: List<WebsiteReadResponse.WebsiteSuccessResponse>,
        val totalCount: Long,
        val pageSize: Int,
        val page: Int,
        val totalPages: Int,
    ) : WebsiteListResponse()

    data class WebsiteListErrorResponse(
        val error: WebsiteResponseError,
    ) : WebsiteListResponse()
}

enum class WebsiteResponseError {
    WEBSITE_NOT_FOUND,
    USER_NOT_FOUND,
    BAD_REQUEST,
    SERVER_ERROR,
}
