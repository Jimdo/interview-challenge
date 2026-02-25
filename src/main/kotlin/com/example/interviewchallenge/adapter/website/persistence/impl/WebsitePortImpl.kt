package com.example.interviewchallenge.adapter.website.persistence.impl

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.interviewchallenge.adapter.website.persistence.mapper.toDomain
import com.example.interviewchallenge.adapter.website.persistence.mapper.toEntity
import com.example.interviewchallenge.adapter.website.persistence.repository.WebsiteJpaRepository
import com.example.interviewchallenge.core.website.model.CreateWebsiteDomain
import com.example.interviewchallenge.core.website.model.WebsiteDomain
import com.example.interviewchallenge.core.website.model.WebsiteError
import com.example.interviewchallenge.core.website.port.WebsitePort
import mu.KotlinLogging
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class WebsitePortImpl(
    private val websiteJpaRepository: WebsiteJpaRepository,
) : WebsitePort {
    private val logger = KotlinLogging.logger {}

    override fun findAll(pageable: Pageable): Page<WebsiteDomain> =
        websiteJpaRepository.findAll(pageable).map { it.toDomain() }

    override fun findById(id: UUID): Either<WebsiteError, WebsiteDomain> {
        val result = websiteJpaRepository.findById(id)
        if (result.isEmpty) {
            return WebsiteError.WEBSITE_NOT_FOUND.left()
        }
        return result.get().toDomain().right()
    }

    override fun findByUserId(userId: UUID, pageable: Pageable): Page<WebsiteDomain> =
        websiteJpaRepository.findAllByUserId(userId, pageable).map { it.toDomain() }

    override fun create(website: CreateWebsiteDomain): Either<WebsiteError, WebsiteDomain> =
        try {
            websiteJpaRepository.save(website.toEntity()).toDomain().right()
        } catch (e: DataIntegrityViolationException) {
            logger.info { "Failed to create website: ${e.message}" }
            WebsiteError.PERSISTENCE_ERROR.left()
        }

    override fun delete(id: UUID): Either<WebsiteError, Unit> =
        try {
            if (!websiteJpaRepository.existsById(id)) {
                WebsiteError.WEBSITE_NOT_FOUND.left()
            } else {
                websiteJpaRepository.deleteById(id)
                Unit.right()
            }
        } catch (e: Exception) {
            logger.error { "Failed to delete website: ${e.message}" }
            WebsiteError.PERSISTENCE_ERROR.left()
        }
}
