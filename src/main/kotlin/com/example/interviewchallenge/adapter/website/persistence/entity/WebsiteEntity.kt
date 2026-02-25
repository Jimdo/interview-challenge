package com.example.interviewchallenge.adapter.website.persistence.entity

import com.example.interviewchallenge.adapter.base.persistence.entity.AuditedBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.util.Objects
import java.util.UUID

@Entity
@Table(name = "website")
class WebsiteEntity(
    override var id: UUID? = null,
    @Column(nullable = false, name = "user_id") var userId: UUID,
) : AuditedBaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as WebsiteEntity
        return id != null && Objects.equals(id, other.id)
    }

    override fun hashCode(): Int = Objects.hash(id)
}
