package com.example.interviewchallenge.adapter.domain.persistence.repository

import com.example.interviewchallenge.adapter.domain.persistence.entity.DomainEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface DomainJpaRepository : JpaRepository<DomainEntity, String> {
    fun findAllByWebsiteId(websiteId: UUID): List<DomainEntity>
}
