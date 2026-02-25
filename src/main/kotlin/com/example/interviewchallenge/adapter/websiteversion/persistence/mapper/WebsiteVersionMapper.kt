package com.example.interviewchallenge.adapter.websiteversion.persistence.mapper

import com.example.interviewchallenge.adapter.websiteversion.persistence.entity.WebsiteVersionEntity
import com.example.interviewchallenge.core.websiteversion.model.CreateWebsiteVersionDomain
import com.example.interviewchallenge.core.websiteversion.model.WebsiteVersionDomain

fun WebsiteVersionEntity.toDomain() =
    WebsiteVersionDomain(
        id = id,
        websiteId = websiteId,
        versionNumber = versionNumber,
        payload = payload,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

fun CreateWebsiteVersionDomain.toEntity(versionNumber: Int) =
    WebsiteVersionEntity(
        id = null,
        websiteId = websiteId,
        versionNumber = versionNumber,
        payload = payload,
    )
