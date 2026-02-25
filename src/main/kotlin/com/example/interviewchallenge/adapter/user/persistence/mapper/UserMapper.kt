package com.example.interviewchallenge.adapter.user.persistence.mapper

import com.example.interviewchallenge.adapter.user.persistence.entity.UserEntity
import com.example.interviewchallenge.core.user.model.CreateUserDomain
import com.example.interviewchallenge.core.user.model.UserDomain

fun UserEntity.toDomain() =
    UserDomain(
        id = id,
        email = email,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

fun CreateUserDomain.toEntity() =
    UserEntity(
        id = null,
        email = email,
    )
