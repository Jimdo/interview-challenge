package com.example.interviewchallenge.adapter.user.persistence.impl

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.interviewchallenge.adapter.user.persistence.mapper.toDomain
import com.example.interviewchallenge.adapter.user.persistence.mapper.toEntity
import com.example.interviewchallenge.adapter.user.persistence.repository.UserJpaRepository
import com.example.interviewchallenge.core.user.model.CreateUserDomain
import com.example.interviewchallenge.core.user.model.UserDomain
import com.example.interviewchallenge.core.user.model.UserError
import com.example.interviewchallenge.core.user.port.UserPort
import mu.KotlinLogging
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserPortImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserPort {
    private val logger = KotlinLogging.logger {}

    override fun findAll(pageable: Pageable): Page<UserDomain> =
        userJpaRepository.findAll(pageable).map { it.toDomain() }

    override fun findById(id: UUID): Either<UserError, UserDomain> {
        val result = userJpaRepository.findById(id)
        if (result.isEmpty) {
            return UserError.USER_NOT_FOUND.left()
        }
        return result.get().toDomain().right()
    }

    override fun create(user: CreateUserDomain): Either<UserError, UserDomain> =
        try {
            userJpaRepository.save(user.toEntity()).toDomain().right()
        } catch (e: DataIntegrityViolationException) {
            logger.info { "Failed to create user: ${e.message}" }
            UserError.PERSISTENCE_ERROR.left()
        }

    override fun delete(id: UUID): Either<UserError, Unit> =
        try {
            if (!userJpaRepository.existsById(id)) {
                UserError.USER_NOT_FOUND.left()
            } else {
                userJpaRepository.deleteById(id)
                Unit.right()
            }
        } catch (e: Exception) {
            logger.error { "Failed to delete user: ${e.message}" }
            UserError.PERSISTENCE_ERROR.left()
        }
}
