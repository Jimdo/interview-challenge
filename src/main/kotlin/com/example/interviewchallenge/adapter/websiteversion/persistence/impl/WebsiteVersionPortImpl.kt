package com.example.interviewchallenge.adapter.websiteversion.persistence.impl

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.interviewchallenge.adapter.websiteversion.persistence.mapper.toDomain
import com.example.interviewchallenge.adapter.websiteversion.persistence.mapper.toEntity
import com.example.interviewchallenge.adapter.websiteversion.persistence.repository.WebsiteVersionJpaRepository
import com.example.interviewchallenge.core.websiteversion.model.CreateWebsiteVersionDomain
import com.example.interviewchallenge.core.websiteversion.model.WebsiteVersionDomain
import com.example.interviewchallenge.core.websiteversion.model.WebsiteVersionError
import com.example.interviewchallenge.core.websiteversion.port.WebsiteVersionPort
import mu.KotlinLogging
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class WebsiteVersionPortImpl(
    private val websiteVersionJpaRepository: WebsiteVersionJpaRepository,
) : WebsiteVersionPort {
    private val logger = KotlinLogging.logger {}

    override fun findByWebsiteId(websiteId: UUID): List<WebsiteVersionDomain> =
        websiteVersionJpaRepository.findAllByWebsiteIdOrderByVersionNumberAsc(websiteId).map { it.toDomain() }

    override fun findByWebsiteIdAndVersionNumber(websiteId: UUID, versionNumber: Int): Either<WebsiteVersionError, WebsiteVersionDomain> {
        val result = websiteVersionJpaRepository.findByWebsiteIdAndVersionNumber(websiteId, versionNumber)
        if (result.isEmpty) {
            return WebsiteVersionError.VERSION_NOT_FOUND.left()
        }
        return result.get().toDomain().right()
    }

    override fun create(version: CreateWebsiteVersionDomain, versionNumber: Int): Either<WebsiteVersionError, WebsiteVersionDomain> =
        try {
            websiteVersionJpaRepository.save(version.toEntity(versionNumber)).toDomain().right()
        } catch (e: DataIntegrityViolationException) {
            logger.info { "Failed to create website version: ${e.message}" }
            WebsiteVersionError.PERSISTENCE_ERROR.left()
        }
}
