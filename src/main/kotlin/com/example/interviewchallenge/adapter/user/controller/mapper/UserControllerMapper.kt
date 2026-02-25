package com.example.interviewchallenge.adapter.user.controller.mapper

import com.example.interviewchallenge.adapter.user.controller.rest.UserCreateRequest
import com.example.interviewchallenge.adapter.user.controller.rest.UserReadResponse
import com.example.interviewchallenge.core.user.model.CreateUserDomain
import com.example.interviewchallenge.core.user.model.UserDomain

fun UserDomain.toResponse() =
    UserReadResponse.UserSuccessResponse(
        id = id!!,
        email = email,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

fun UserCreateRequest.toDomain() =
    CreateUserDomain(
        email = email,
    )
