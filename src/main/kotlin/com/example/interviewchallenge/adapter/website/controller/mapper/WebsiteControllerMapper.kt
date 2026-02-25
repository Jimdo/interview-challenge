package com.example.interviewchallenge.adapter.website.controller.mapper

import com.example.interviewchallenge.adapter.website.controller.rest.WebsiteCreateRequest
import com.example.interviewchallenge.adapter.website.controller.rest.WebsiteReadResponse
import com.example.interviewchallenge.core.website.model.CreateWebsiteDomain
import com.example.interviewchallenge.core.website.model.WebsiteDomain

fun WebsiteDomain.toResponse() =
    WebsiteReadResponse.WebsiteSuccessResponse(
        id = id!!,
        userId = userId,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

fun WebsiteCreateRequest.toDomain() =
    CreateWebsiteDomain(
        userId = userId,
    )
