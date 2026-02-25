package com.example.interviewchallenge.adapter.domain.persistence.impl

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.interviewchallenge.adapter.domain.persistence.mapper.toDomain
import com.example.interviewchallenge.adapter.domain.persistence.mapper.toEntity
import com.example.interviewchallenge.adapter.domain.persistence.repository.DomainJpaRepository
import com.example.interviewchallenge.core.domain.model.CreateDomainDomain
import com.example.interviewchallenge.core.domain.model.DomainDomain
import com.example.interviewchallenge.core.domain.model.DomainError
import com.example.interviewchallenge.core.domain.port.DomainPort
import mu.KotlinLogging
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DomainPortImpl(
    private val domainJpaRepository: DomainJpaRepository,
) : DomainPort {
    private val logger = KotlinLogging.logger {}

    override fun findByWebsiteId(websiteId: UUID): List<DomainDomain> =
        domainJpaRepository.findAllByWebsiteId(websiteId).map { it.toDomain() }

    override fun findByDomain(domain: String): Either<DomainError, DomainDomain> {
        val result = domainJpaRepository.findById(domain)
        if (result.isEmpty) {
            return DomainError.DOMAIN_NOT_FOUND.left()
        }
        return result.get().toDomain().right()
    }

    override fun create(domain: CreateDomainDomain): Either<DomainError, DomainDomain> =
        try {
            domainJpaRepository.save(domain.toEntity()).toDomain().right()
        } catch (e: DataIntegrityViolationException) {
            logger.info { "Failed to create domain: ${e.message}" }
            DomainError.PERSISTENCE_ERROR.left()
        }

    override fun delete(domain: String): Either<DomainError, Unit> =
        try {
            if (!domainJpaRepository.existsById(domain)) {
                DomainError.DOMAIN_NOT_FOUND.left()
            } else {
                domainJpaRepository.deleteById(domain)
                Unit.right()
            }
        } catch (e: Exception) {
            logger.error { "Failed to delete domain: ${e.message}" }
            DomainError.PERSISTENCE_ERROR.left()
        }
}
