package com.example.interviewchallenge.adapter.domain.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.Objects
import java.util.UUID

@Entity
@Table(name = "domain")
@EntityListeners(AuditingEntityListener::class)
class DomainEntity(
    @Id
    @Column(nullable = false, length = 255)
    var domain: String,
    @Column(nullable = false, name = "website_id") var websiteId: UUID,
    @Column(insertable = true, updatable = false, nullable = false)
    @CreatedDate
    var createdAt: Instant = Instant.now(),
    @Column(insertable = true, updatable = true, nullable = false)
    @LastModifiedDate
    var updatedAt: Instant = Instant.now(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as DomainEntity
        return Objects.equals(domain, other.domain)
    }

    override fun hashCode(): Int = Objects.hash(domain)
}
