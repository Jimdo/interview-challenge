package com.example.interviewchallenge.adapter.user.persistence.entity

import com.example.interviewchallenge.adapter.base.persistence.entity.AuditedBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.util.Objects
import java.util.UUID

@Entity
@Table(name = "app_user")
class UserEntity(
    override var id: UUID? = null,
    @Column(nullable = false, unique = true, length = 255) var email: String,
) : AuditedBaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as UserEntity
        return Objects.equals(email, other.email)
    }

    override fun hashCode(): Int = Objects.hash(email)
}
