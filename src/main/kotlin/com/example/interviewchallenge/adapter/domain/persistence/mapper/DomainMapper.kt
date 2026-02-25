package com.example.interviewchallenge.adapter.domain.persistence.mapper

import com.example.interviewchallenge.adapter.domain.persistence.entity.DomainEntity
import com.example.interviewchallenge.core.domain.model.CreateDomainDomain
import com.example.interviewchallenge.core.domain.model.DomainDomain

fun DomainEntity.toDomain() =
    DomainDomain(
        domain = domain,
        websiteId = websiteId,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

fun CreateDomainDomain.toEntity() =
    DomainEntity(
        domain = domain,
        websiteId = websiteId,
    )
