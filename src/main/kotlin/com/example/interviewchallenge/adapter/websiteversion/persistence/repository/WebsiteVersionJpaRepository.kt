package com.example.interviewchallenge.adapter.websiteversion.persistence.repository

import com.example.interviewchallenge.adapter.websiteversion.persistence.entity.WebsiteVersionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface WebsiteVersionJpaRepository : JpaRepository<WebsiteVersionEntity, UUID> {
    fun findAllByWebsiteIdOrderByVersionNumberAsc(websiteId: UUID): List<WebsiteVersionEntity>

    fun findByWebsiteIdAndVersionNumber(websiteId: UUID, versionNumber: Int): Optional<WebsiteVersionEntity>
}
