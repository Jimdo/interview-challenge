package com.example.interviewchallenge.adapter.websiteversion.persistence.entity

import com.example.interviewchallenge.adapter.base.persistence.entity.AuditedBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.util.Objects
import java.util.UUID

@Entity
@Table(
    name = "website_version",
    uniqueConstraints = [UniqueConstraint(columnNames = ["website_id", "version_number"])],
)
class WebsiteVersionEntity(
    override var id: UUID? = null,
    @Column(nullable = false, name = "website_id") var websiteId: UUID,
    @Column(nullable = false, name = "version_number") var versionNumber: Int,
    @Column(nullable = false, columnDefinition = "TEXT") var payload: String,
) : AuditedBaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as WebsiteVersionEntity
        return Objects.equals(websiteId, other.websiteId) && versionNumber == other.versionNumber
    }

    override fun hashCode(): Int = Objects.hash(websiteId, versionNumber)
}
