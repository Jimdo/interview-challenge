package com.example.interviewchallenge.adapter.base.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Version
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.UUID

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AuditedBaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: UUID? = null,
    @Column(insertable = true, updatable = false, nullable = false)
    @CreatedDate
    open var createdAt: Instant = Instant.now(),
    @Column(insertable = true, updatable = true, nullable = false)
    @LastModifiedDate
    open var updatedAt: Instant = Instant.now(),
    @Column(nullable = false)
    @Version
    open var version: Int = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as AuditedBaseEntity
        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
