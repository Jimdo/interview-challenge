package com.example.interviewchallenge.adapter.user.controller.rest

import java.time.Instant
import java.util.UUID

sealed class UserReadResponse {
    data class UserSuccessResponse(
        val id: UUID,
        val email: String,
        val createdAt: Instant,
        val updatedAt: Instant,
    ) : UserReadResponse()

    data class UserErrorResponse(
        val error: UserResponseError,
    ) : UserReadResponse()
}

sealed class UserListResponse {
    data class UserListSuccessResponse(
        val users: List<UserReadResponse.UserSuccessResponse>,
        val totalCount: Long,
        val pageSize: Int,
        val page: Int,
        val totalPages: Int,
    ) : UserListResponse()

    data class UserListErrorResponse(
        val error: UserResponseError,
    ) : UserListResponse()
}

enum class UserResponseError {
    USER_NOT_FOUND,
    BAD_REQUEST,
    SERVER_ERROR,
}
