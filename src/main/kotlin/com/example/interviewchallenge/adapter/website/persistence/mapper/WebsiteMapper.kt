package com.example.interviewchallenge.adapter.website.persistence.mapper

import com.example.interviewchallenge.adapter.website.persistence.entity.WebsiteEntity
import com.example.interviewchallenge.core.website.model.CreateWebsiteDomain
import com.example.interviewchallenge.core.website.model.WebsiteDomain

fun WebsiteEntity.toDomain() =
    WebsiteDomain(
        id = id,
        userId = userId,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

fun CreateWebsiteDomain.toEntity() =
    WebsiteEntity(
        id = null,
        userId = userId,
    )
