package com.example.interviewchallenge.adapter.user.controller.rest

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserCreateRequest(
    @field:NotBlank
    @field:Email
    @field:Size(max = 255)
    val email: String,
)
