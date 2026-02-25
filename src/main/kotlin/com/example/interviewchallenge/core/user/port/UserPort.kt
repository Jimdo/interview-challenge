package com.example.interviewchallenge.core.user.port

import arrow.core.Either
import com.example.interviewchallenge.core.user.model.CreateUserDomain
import com.example.interviewchallenge.core.user.model.UserDomain
import com.example.interviewchallenge.core.user.model.UserError
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

interface UserPort {
    fun findAll(pageable: Pageable): Page<UserDomain>

    fun findById(id: UUID): Either<UserError, UserDomain>

    fun create(user: CreateUserDomain): Either<UserError, UserDomain>

    fun delete(id: UUID): Either<UserError, Unit>
}
